(function() {
    'use strict';

    angular
        .module('jhipsterRestcodingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('registry', {
            parent: 'entity',
            url: '/registry?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterRestcodingApp.registry.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/registry/registries.html',
                    controller: 'RegistryController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('registry');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('registry-detail', {
            parent: 'entity',
            url: '/registry/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterRestcodingApp.registry.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/registry/registry-detail.html',
                    controller: 'RegistryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('registry');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Registry', function($stateParams, Registry) {
                    return Registry.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'registry',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('registry-detail.edit', {
            parent: 'registry-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/registry/registry-dialog.html',
                    controller: 'RegistryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Registry', function(Registry) {
                            return Registry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('registry.new', {
            parent: 'registry',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/registry/registry-dialog.html',
                    controller: 'RegistryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                string_id: null,
                                string: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('registry', null, { reload: 'registry' });
                }, function() {
                    $state.go('registry');
                });
            }]
        })
        .state('registry.edit', {
            parent: 'registry',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/registry/registry-dialog.html',
                    controller: 'RegistryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Registry', function(Registry) {
                            return Registry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('registry', null, { reload: 'registry' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('registry.delete', {
            parent: 'registry',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/registry/registry-delete-dialog.html',
                    controller: 'RegistryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Registry', function(Registry) {
                            return Registry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('registry', null, { reload: 'registry' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
