(function () {
    'use strict';

    angular
        .module('GroceriesCoachApp.commonUtilities')
        .factory('serviceUtility', serviceUtility);

    serviceUtility.$inject = ['$http', '$log', '$mdToast', '$mdDialog'];

    function serviceUtility($http, $log, $mdToast, $mdDialog) {
        var service = {
            processSuccessResponse: processSuccessResponse,
            processErrorResponse: processErrorResponse,
            showToast: showToast,
            showNotificationMessages: showNotificationMessages,
            showWait: showWait,
            hideWait: hideWait
        };

        return service;


        function processSuccessResponse(response) {
            $log.info(response.data.messages);
            showNotificationMessages(response.data.messages);
            return response.data.payload;
        }

        function showNotificationMessages(messages) {
            angular.forEach(messages, function (message) {
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

        function showWait() {
            $mdDialog.show({
                // controller: 'waitCtrl',
                template:
                '<md-dialog style="background-color:transparent;box-shadow:none">' +
                    '<div layout="row" layout-sm="column" layout-align="center center" aria-label="wait" style="height: 100px;">' +
                        '<md-progress-circular md-mode="indeterminate"></md-progress-circular>' +
                    '</div>' +
                '</md-dialog>',
                parent: angular.element(document.body),
                clickOutsideToClose: false,
                fullscreen: false
            }).then(function (answer) {

            });
        }

        function hideWait(){
            $mdDialog.cancel();
        }
    }
})();