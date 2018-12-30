(function() {
    'use strict';

    angular
        .module('xuanhaApp')
        .controller('StudentDialogController', StudentDialogController);

    StudentDialogController.$inject = ['$rootScope', '$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity','Student', 'Lop', '$log', '$filter'];

    function StudentDialogController ($rootScope, $timeout, $scope, $stateParams, $uibModalInstance, entity,Student, Lop, $log, $filter) {
        var vm = this;

        vm.student = entity;

        vm.lops = [];
        loadAll();

        function loadAll() {
            Lop.query(function(result) {
                vm.lops = result;

                console.log(vm.lops);
            });
        }

        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.student.id !== null) {
                Student.update(vm.student, onSaveSuccess, onSaveError);
            } else {
                Student.save(vm.student, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('xuanhaApp:studentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();
