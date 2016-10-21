(function() {
    'use strict';

    angular
        .module('jhipsterRestcodingApp')
        .controller('RegistryDetailController', RegistryDetailController);

    RegistryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Registry'];

    function RegistryDetailController($scope, $rootScope, $stateParams, previousState, entity, Registry) {
        var vm = this;

        vm.registry = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jhipsterRestcodingApp:registryUpdate', function(event, result) {
            vm.registry = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
