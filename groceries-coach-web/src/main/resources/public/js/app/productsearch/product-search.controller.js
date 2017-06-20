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
        vm.products = [];
        vm.sortTypes = [];
        vm.storeTypeToStoresMap = {};

        vm.searchForProducts = searchForProducts;


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
                vm.products = result;
            });
        }
    }
})();
