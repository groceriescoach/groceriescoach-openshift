(function () {
    'use strict';

    angular
        .module('GroceriesCoachApp.productSearch')
        .factory('productSearchService', productSearchService);

    productSearchService.$inject = ['$http', '$log', 'serviceUtility'];

    function productSearchService($http, $log, serviceUtility) {

        var service = {
            searchForProducts: searchForProducts,
            getSortTypes: getSortTypes,
            getStores: getStores
        };

        return service;


        ///////////////////////////////////////////////////////////////////////////////

        function searchForProducts(searchCriteria) {
            var url = "/search";
            return $http.get(url, {params: searchCriteria}).then(
                function (response) {
                    $log.log(response);
                    return response.data;
                },
                function (response) {
                    serviceUtility.processErrorResponse(response);
                }
            );
        }


        function getSortTypes() {
            var url = "/sort-types";
            return $http.get(url).then(
                function (response) {
                    return response.data;
                },
                function (response) {
                    $log.error(response);
                }
            );
        }


        function getStores() {
            var url = "/stores";
            return $http.get(url).then(
                function (response) {
                    return response.data;
                },
                function (response) {
                    $log.error(response);
                }
            );
        }


    }
})();
