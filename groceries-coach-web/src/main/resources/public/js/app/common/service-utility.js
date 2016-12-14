(function() {
    'use strict';

    angular
        .module('GroceriesCoachApp.commonUtilities')
        .factory('serviceUtility', serviceUtility);

    serviceUtility.$inject = ['$http', '$log', '$mdToast'];

    function serviceUtility($http, $log, $mdToast) {
        
        var service = {
            processSuccessResponse: processSuccessResponse,
            processErrorResponse: processErrorResponse
        };
        
        return service;
        

        function processSuccessResponse(response) {
            $log.info(response.data.notificationMessages);
            return response.data.payload;
        }


        function processErrorResponse(response) {
            $log.error(response.config);
            $log.error(response.config.headers);
            $log.error(response.config.method);
            $log.error(response.config.url);

            $log.error(response.data);

/*
            $scope.showCustomToast = function() {
                $mdToast.show({
                    templateUrl: 'error-toast-tmpl.html',
                    hideDelay: 6000,
                    position: 'top right'
                });
            };
*/


            $mdToast.show(
                $mdToast
                    .simple()
                    .textContent(response.data.error + ' - ' + response.data.message + ' - ' + response.data.exception)
                    .position('top right')
                    .hideDelay(5000)
            );

            $log.error(response.status);
            $log.error(response.statusText);
        }
    }
})();