var Utils = (function () {

  function toFirstUpper (str) {
    if (!str) {
      return '';
    }

    return str.slice(0, 1).toUpperCase() + str.slice(1);
  }

  function getFormDataToJSON (array) {
    array = array || {};
    
    return array.reduce(function (result, obj) {
    	result[obj.name] = obj.value;
    	return result;
    }, {});
  }

  return {
    toFirstUpper: toFirstUpper,
    getFormDataToJSON: getFormDataToJSON
  };
})();
