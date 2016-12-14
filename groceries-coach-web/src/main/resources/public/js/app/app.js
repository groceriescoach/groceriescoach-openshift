angular
    .module('GroceriesCoachApp',
        ['ngMaterial', 'ui.router', 'ngMessages', 'GroceriesCoachApp.productSearch',
            'GroceriesCoachApp.security', 'GroceriesCoachApp.widgets', 'GroceriesCoachApp.commonUtilities'])
    .config(['$stateProvider', '$urlRouterProvider', '$mdThemingProvider', '$mdIconProvider', '$httpProvider', function ($stateProvider, $urlRouterProvider, $mdThemingProvider, $mdIconProvider, $httpProvider) {

        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

        //  For any unmatched url, redirect to home
        $urlRouterProvider.otherwise("/product-search");

        //  Now set up the states
        $stateProvider
            .state("product-search", {
                url: "/product-search",
                templateUrl: "js/app/productsearch/product-search.html"
            })
            .state("login", {
                url: "/login",
                templateUrl: "js/app/security/login.html"
            })
        ;


        $mdIconProvider
            .defaultIconSet("images/svg/avatars.svg", 128)
            .icon("menu", "images/svg/menu.svg", 24)
            .icon("share", "images/svg/share.svg", 24)
            .icon("google_plus", "images/svg/google_plus.svg", 512)
            .icon("hangouts", "images/svg/hangouts.svg", 512)
            .icon("twitter", "images/svg/twitter.svg", 512)
            .icon("phone", "images/svg/phone.svg", 512);

        $mdThemingProvider.theme('default')
            .primaryPalette('orange')
            .accentPalette('red');
    }]);
