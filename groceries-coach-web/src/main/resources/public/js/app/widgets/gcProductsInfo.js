(function() {

    'use strict';

    angular
        .module('GroceriesCoachApp.widgets')
        .directive('gcProductsInfo', gcProductsInfo);


    //  Usage:
    //  <div gc-products-info products="products"></div>
    //
    function gcProductsInfo() {

        var directive = {
            restrict: 'EA',
            scope: {
                products: '='
            },
            replace: true,
            templateUrl: 'js/app/widgets/products-info.html'
        };

        return directive;

    }
})();
