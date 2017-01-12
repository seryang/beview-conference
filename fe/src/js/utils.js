var Utils = (function () {

  function toFirstUpper(str) {
    if (!str) {
      return '';
    }

    return str.slice(0, 1).toUpperCase() + str.slice(1);
  }

  return {
    toFirstUpper: toFirstUpper
  };
})();
