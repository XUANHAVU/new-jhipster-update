(function() {
    'use strict';

    angular
        .module('xuanhaApp')
        .controller('GvDetailController', GvDetailController);

    GvDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Gv'];

    function GvDetailController($scope, $rootScope, $stateParams, previousState, entity, Gv) {
        var vm = this;

        vm.gv = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('xuanhaApp:gvUpdate', function(event, result) {
            vm.gv = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
