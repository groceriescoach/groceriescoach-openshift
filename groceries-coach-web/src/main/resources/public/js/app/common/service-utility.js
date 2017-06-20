(function() {
    'use strict';

    angular
        .module('GroceriesCoachApp.commonUtilities')
        .factory('serviceUtility', serviceUtility);

    serviceUtility.$inject = ['$http', '$log', '$mdToast'];

    function serviceUtility($http, $log, $mdToast) {
        
        var service = {
            processSuccessResponse: processSuccessResponse,
            processErrorResponse: processErrorResponse,
            showToast: showToast,
            showNotificationMessages: showNotificationMessages
        };
        
        return service;
        

        function processSuccessResponse(response) {
            $log.info(response.data.messages);
            showNotificationMessages(response.data.messages);
            return response.data.payload;
        }

        function showNotificationMessages(messages) {
            angular.forEach(messages, function(message) {
                showToast(message);
            })
        }

        function processErrorResponse(response) {
            $log.error(response.config);
            $log.error(response.config.headers);
            $log.error(response.config.method);
            $log.error(response.config.url);
            $log.error(response.data);
            showToast(response.data.error + ' - ' + response.data.message + ' - ' + response.data.exception);
            $log.error(response.status);
            $log.error(response.statusText);
        }

        function showToast(message) {
            $mdToast.show(
                $mdToast
                    .simple()
                    .textContent(message)
                    .position('top right')
                    .hideDelay(5000)
            );

        }
    }
})();