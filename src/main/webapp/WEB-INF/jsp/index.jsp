<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Foxway test</title>
    <link href="/css/style.css" rel="stylesheet" />
    <script src="/js/session.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
</head>

<script>
    function submit() {
        var data = $('#input').val().split("\n").map(function (line) {
            return line.split(",").map(function (value) {
                return parseInt(value.trim());
            });
        });
        var session = new Session(data);
        session.onComplete = function (responses) {
            $('#output').val('');
            for (var i=0; i<responses["MIN"].length; i++)
                $('#output').val($('#output').val() + "1: " + responses["MIN"][i] + ", 2: " + responses["MIN_NO_FIRST_LAST_BESIDE"][i] + "\n")
        }
        session.execute();
    }

    $( document ).ready(function() {
        $('#input').keydown(function (e) {
            if (e.ctrlKey && e.keyCode == 13) {
                submit();
            }
        });

        $('#submitButton').click(function (e) {
            submit();
        });
    });

</script>
<body>

<div class="container">
    <div class="left-half">
        <textarea id="input"></textarea>
    </div>
    <div class="right-half">
        <textarea readonly id="output"></textarea>
    </div>
    <div class="controls">
        <input id="submitButton" type="submit">&nbsp;<span class="label">or press Ctrl-Enter</span>
    </div>
</div>

</body>
</html>