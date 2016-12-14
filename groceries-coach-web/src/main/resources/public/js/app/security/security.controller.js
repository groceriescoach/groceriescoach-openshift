(function () {

    angular
        .module('GroceriesCoachApp.security')
        .controller('SecurityController', ['$http', '$log', '$rootScope', '$state', SecurityController]);

    function SecurityController($http, $log, $rootScope, $state) {

        var vm = this;

        vm.credentials = {};
        vm.authenticate = authenticate;
        vm.logout = logout;
        vm.isAuthenticated = isAuthenticated;
        activate();

        function activate() {
        }


        function authenticate() {

            $log.log(vm.credentials);

            var headers = {};

            if (vm.credentials.username && vm.credentials.password) {
                headers.authorization = "Basic " + btoa(vm.credentials.username + ":" + vm.credentials.password);
            }

            $log.log(headers);

            $http.get("/user", {
                headers: headers
            }).success(function (data) {
                $log.log("Successfully authenticated: " + data);
                $rootScope.authenticated = true;
                $state.go("product-search");
            }).error(function (data) {
                $log.log("Failed authentication:" + data);
            })
        }


        function logout() {
            $http.post('logout').success(function () {
                $rootScope.authenticated = false;
                $state.go("login");
            }).error(function (data) {
                $log.log("Logout failed");
                $rootScope.authenticated = false;
            });
        }

        function isAuthenticated() {
            return $rootScope.authenticated;
        }
    }
})();
