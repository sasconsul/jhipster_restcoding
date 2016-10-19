(function () {
    'use strict';

    angular
        .module('jhipsterRestcodingApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
