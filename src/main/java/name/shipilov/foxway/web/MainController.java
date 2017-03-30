package name.shipilov.foxway.web;

import name.shipilov.foxway.service.ArrayService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@EnableAutoConfiguration
@ComponentScan("name.shipilov.foxway")
@Controller
public class MainController {

	public static class Request {

		enum Algorithm {
			MIN, MIN_NO_FIRST_LAST_BESIDE
		}

		public Algorithm getAlgorithm() {
			return algorithm;
		}

		public void setAlgorithm(Algorithm algorithm) {
			this.algorithm = algorithm;
		}

		private Algorithm algorithm;
		private List<Integer> data;

		public List<Integer> getData() {
			return data;
		}

		public void setData(List<Integer> data) {
			this.data = data;
		}

	}

	public static class Response {

		enum Status {
			OK, ERROR
		}

		private Status status;
		private int result;
		private String error;

		public Status getStatus() {
			return status;
		}

		public void setStatus(Status status) {
			this.status = status;
		}

		public int getResult() {
			return result;
		}

		public void setResult(int result) {
			this.result = result;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}

	}

	private static Log log = LogFactory.getLog(MainController.class);

	@Autowired
	private ArrayService arrayService;

	@RequestMapping(method = RequestMethod.POST, value="/min")
	public @ResponseBody Response process(@RequestBody Request request) {
		final Response response = new Response();
		try {
			response.setResult(request.getAlgorithm() == Request.Algorithm.MIN ? arrayService.minimalSumOfTwoElements(request.getData())
					: arrayService.minimalSumOfTwoElementsNoFirstLastBeside(request.getData()));
			response.setStatus(Response.Status.OK);
			return response;
		} catch (Exception e) {
			response.setStatus(Response.Status.ERROR);
			response.setError(e.getMessage());
			return response;
		}
	}

	@RequestMapping("/")
	public String index(Map<String, Object> model) {
		return "index";
	}

	//boot
	public static void main(String[] args) throws Exception {
		SpringApplication.run(MainController.class, args);
	}

}