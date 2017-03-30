/**
 * Класс инкапсулирует логику отправки запросов и сохранения полученных результатов.
 * Запросы уходят асинхронно ajax'ом.
 * Когда все расчеты выполнены, клиент получает результаты посредством callback-функции.
 *
 * Код клиента будет выглядеть так:
 * session = new Session( <массивы с поля ввода> );
 * session.onComplete = function(results) { <отображение результатов в поле вывода> };
 * session.execute();
 *
 * @param data двумерный массив целых чисел
 * @constructor
 */
function Session(data) {
    this.data = data;
    //в результатах храним сразу строковое представление (результат или ошибку)
    this.responses = {};
    this.responses["MIN"] = new Array(data.length);
    this.responses["MIN_NO_FIRST_LAST_BESIDE"] = new Array(data.length);
    //будем декрeментировать счетчик по получению ответов
    this.responsesWaitingCounter = data.length * 2;
}

/**
 * Вызывается, когда все ответы получены
 */
Session.prototype.onComplete = function(responses) {
    //клиент определяет функцию обработки результатов
}

/**
 * Выполняет запросы к серверу, сохраняя результаты на своих местах
 */
Session.prototype.execute = function() {
    var _this = this;
    this.data.forEach(function(item, i, arr) {
        _this.sendRequest(item, i, "MIN");
        _this.sendRequest(item, i, "MIN_NO_FIRST_LAST_BESIDE");
    });
};

/**
 * Отправка запроса для указанного массива и алгоритма
 *
 * @param data массив чисел
 * @param dataIndex порядковый номер массива чисел
 * @param algorithm алгоритм: MIN, MIN_NO_FIRST_LAST_BESIDE
 */
Session.prototype.sendRequest = function(data, dataIndex, algorithm) {
    $.ajax({
        session: this,
        requestDataIndex: dataIndex,
        requestAlgorithm: algorithm,
        url: '/min',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({ "algorithm": algorithm, "data": data }),
        type: "POST",
        success: function(data){
            this.session.registerResponse(this.requestAlgorithm, this.requestDataIndex, data.status == 'OK' ? data.result : data.error);
        },
        error: function(){
            this.session.registerResponse(this.requestAlgorithm, this.requestDataIndex, "transport error"); //TODO details
        }
    });
}

Session.prototype.registerResponse = function(algorithm, dataIndex, value) {
    this.responses[algorithm][dataIndex] = value;
    this.responsesWaitingCounter--;
    if (this.responsesWaitingCounter == 0) {
        this.onComplete(this.responses);
    }
}
