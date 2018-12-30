(function() {
    'use strict';

    angular
        .module('xuanhaApp')
        .controller('StudentDetailController', StudentDetailController);

    StudentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Student', '$log'];

    function StudentDetailController($scope, $rootScope, $stateParams, previousState, entity, Student, $log) {
        var vm = this;

        vm.student = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('xuanhaApp:studentUpdate', function(event, result) {
            vm.student = result;
        });

        $scope.$on('$destroy', unsubscribe);
    }
})();
