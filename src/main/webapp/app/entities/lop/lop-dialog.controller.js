(function() {
    'use strict';

    angular
        .module('xuanhaApp')
        .controller('LopDialogController', LopDialogController);

    LopDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Lop', 'Student'];

    function LopDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Lop, Student) {
        var vm = this;

        vm.lop = entity;
        vm.clear = clear;
        vm.save = save;
        vm.students = Student.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.lop.id !== null) {
                Lop.update(vm.lop, onSaveSuccess, onSaveError);
            } else {
                Lop.save(vm.lop, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('xuanhaApp:lopUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
