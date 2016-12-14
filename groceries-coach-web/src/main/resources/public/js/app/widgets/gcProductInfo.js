(function() {

    'use strict';

    angular
        .module('GroceriesCoachApp.widgets')
        .directive('gcProductInfo', gcProductInfo);


    //  Usage:
    //  <div gc-product-info products="vm.products"></div>
    //
    function gcProductInfo() {

        var directive = {
            scope: {
                product: '='
            },
            replace: true,
            templateUrl: 'js/app/widgets/product-info.html',
            restrict: 'EA'
        };

        return directive;
    }
})();
