(function() {
    'use strict';

    angular
        .module('xuanhaApp')
        .controller('LopDetailController', LopDetailController);

    LopDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Lop', 'Student'];

    function LopDetailController($scope, $rootScope, $stateParams, previousState, entity, Lop, Student) {
        var vm = this;

        vm.lop = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('xuanhaApp:lopUpdate', function(event, result) {
            vm.lop = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
