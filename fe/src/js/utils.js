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

  var timeMap = {
    T1: '09:00 ~ 10:50',
    T2: '11:00 ~ 12:50',
    T3: '14:00 ~ 15:50',
    T4: '16:00 ~ 17:50'
  };

  function getTimeInfoFromKey (key) {
    return timeMap[key];
  }

  /**
   * array 에서 해당 item 을 가지고 있는지 판별함
   *
   * @param array [Array]
   * @param item [Object]
   * @param prop [string] item 에서 비교할 기준이 되는 key
   */
  function has (array, item, prop) {
    var result = false;
    for(var i = 0; i < array.length; i++) {
      if(array[i][prop] === item[prop]) {
        result = true;
        break;
      }
    }
    return result;
  }

  function unique (array, prop) {
    if (!array) {
      return [];
    }

    return array.reduce(function (result, item) {
      if (!has(result, item, prop)) {
        result.push(item);
      }
      return result;
    }, []);
  }

  return {
    toFirstUpper: toFirstUpper,
    getFormDataToJSON: getFormDataToJSON,
    getTimeInfoFromKey: getTimeInfoFromKey,
    unique: unique
  };
})();
