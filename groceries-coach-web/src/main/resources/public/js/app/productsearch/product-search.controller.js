(function () {

    angular
        .module('GroceriesCoachApp.productSearch')
        .controller('ProductSearchController', ProductSearchController);


    ProductSearchController.$inject = ['productSearchService', '$mdToast', '$log'];

    /**
     * Main Controller for the Angular Material Starter App
     */
    function ProductSearchController(productSearchService, $mdToast, $log) {

        var vm = this;

        vm.searchCriteria = {stores: []};
        vm.searchResults = null;
        vm.sortTypes = [];
        vm.storeTypeToStoresMap = {};

        vm.searchForProducts = searchForProducts;

        vm.searchResultsContainsUnitPriceAndNonUnitPriceProducts = searchResultsContainsUnitPriceAndNonUnitPriceProducts;
        vm.searchResultsContainsUnitPriceProducts = searchResultsContainsUnitPriceProducts;
        vm.searchResultsContainsNonUnitPriceProducts = searchResultsContainsNonUnitPriceProducts;
        vm.searchResultsContainsOnlyUnitPriceProducts = searchResultsContainsOnlyUnitPriceProducts;
        vm.searchResultsContainsOnlyNonUnitPriceProducts = searchResultsContainsOnlyNonUnitPriceProducts;

        activate();


        vm.toggle = function (item) {
            var idx = vm.searchCriteria.stores.indexOf(item);
            if (idx > -1) {
                vm.searchCriteria.stores.splice(idx, 1);
            } else {
                vm.searchCriteria.stores.push(item);
            }
        };
        vm.exists = function (item) {
            return vm.searchCriteria.stores.indexOf(item) > -1;
        };

        // *********************************
        // Internal methods
        // *********************************

        function activate() {
            getSortTypes();
            getStores();
        }

        function getSortTypes() {
            productSearchService.getSortTypes().then(function (result) {
                vm.sortTypes = result;
            })
        }

        function getStores() {
            productSearchService.getStores().then(function (result) {
                vm.storeTypeToStoresMap = result;
            })
        }

        function searchForProducts() {
            productSearchService.searchForProducts(vm.searchCriteria).then(function (result) {
                vm.searchResults = result;
            });
        }

        function searchResultsContainsUnitPriceAndNonUnitPriceProducts() {
            return searchResultsContainsUnitPriceProducts() && searchResultsContainsNonUnitPriceProducts();
        }

        function searchResultsContainsUnitPriceProducts() {
            return (vm.searchResults && vm.searchResults.productsWithUnitPrices && vm.searchResults.productsWithUnitPrices.length);
        }

        function searchResultsContainsNonUnitPriceProducts() {
            return (vm.searchResults && vm.searchResults.productsWithoutUnitPrices && vm.searchResults.productsWithoutUnitPrices.length);
        }

        function searchResultsContainsOnlyUnitPriceProducts() {
            return searchResultsContainsUnitPriceProducts() && !searchResultsContainsNonUnitPriceProducts();
        }

        function searchResultsContainsOnlyNonUnitPriceProducts() {
            return searchResultsContainsNonUnitPriceProducts() && !searchResultsContainsUnitPriceProducts();
        }
    }
})();
