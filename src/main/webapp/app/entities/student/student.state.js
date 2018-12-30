(function () {
    'use strict';

    angular
        .module('xuanhaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('student', {
                parent: 'entity',
                url: '/student',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Students'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/student/students.html',
                        controller: 'StudentController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                }
            })
            .state('student-detail', {
                parent: 'student',
                url: '/student/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Student'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/student/student-detail.html',
                        controller: 'StudentDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Student', function ($stateParams, Student) {
                        return Student.get({id: $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'student',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('student-detail.edit', {
                parent: 'student-detail',
                url: '/detail/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/student/student-dialog.html',
                        controller: 'StudentDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['Student', function (Student) {
                                return Student.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('student.new', {
                parent: 'student',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/student/student-dialog.html',
                        controller: 'StudentDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    lopName: 1,
                                    lopId: 1,
                                    nameStudent: null,
                                    ageStudent: null,
                                    address: null,
                                    phone: null,
                                    email: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function () {
                        $state.go('student', null, {reload: 'student'});
                    }, function () {
                        $state.go('student');
                    });
                }]
            })
            .state('student.edit', {
                parent: 'student',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/student/student-dialog.html',
                        controller: 'StudentDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['Student', function (Student) {
                                return Student.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('student', null, {reload: 'student'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })

            .state('student.delAStudent', {
                parent: 'student',
                url: '/{id}/del',
                data: {
                    authorities: ['ROLE_USER']
                },
                //templateUrl: 'app/entities/student/students.html',
                //controller: 'StudentDeleteNowController',
                // controllerAs: 'vm',
                resolve: {
                    entity: ['Student', '$stateParams','$state', function (Student, $stateParams, $state) {
                        return Student.delete({id: $stateParams.id}, function () {
                            $state.go('student', null, {reload: 'student'});
                        });
                    }]
                }
            })


            .state('student.delete', {
                parent: 'student',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/student/student-delete-dialog.html',
                        controller: 'StudentDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['Student', function (Student) {
                                return Student.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('student', null, {reload: 'student'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            });
    }

})();
