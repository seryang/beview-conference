var http=function(){"use strict";function t(t,a){for(var r,e=t;null!==(r=n.exec(t));){var u=r[1],i=r[2];e=t.replace(u,a[i])}return e}var a={},r="/api",e=["get","post","put","delete"],n=/(\{(\w+)\})/g;return e.forEach(function(e){a[e]=function(a){return a.params&&(a.url=t(a.url,a.params)),a.data&&!a.isFileServer&&(a.data=JSON.stringify(a.data)),a=Object.assign({method:e,contentType:"application/json",dataType:"json",timeout:5e3},a,{url:r+a.url}),$.ajax(a)}}),a}();
//# sourceMappingURL=data:application/json;charset=utf8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIi9zcmMvanMvaHR0cC5qcyJdLCJuYW1lcyI6WyJodHRwIiwiY29udmVydFVybCIsInVybCIsInBhcmFtcyIsIm1hdGNoZXMiLCJjb252ZXJ0ZWQiLCJyZVBhcmFtIiwiZXhlYyIsIm1hdGNoZWQiLCJrZXkiLCJyZXBsYWNlIiwicHVibGljQXBpIiwiQkFTRV9VUkwiLCJtZXRob2RzIiwiZm9yRWFjaCIsIm1ldGhvZCIsIm9wdGlvbnMiLCJkYXRhIiwiaXNGaWxlU2VydmVyIiwiSlNPTiIsInN0cmluZ2lmeSIsIk9iamVjdCIsImFzc2lnbiIsImNvbnRlbnRUeXBlIiwiZGF0YVR5cGUiLCJ0aW1lb3V0IiwiJCIsImFqYXgiXSwibWFwcGluZ3MiOiJBQUFBLEdBQUlBLE1BQU8sV0FDVCxZQXFCQSxTQUFTQyxHQUFZQyxFQUFLQyxHQUl4QixJQUhBLEdBQ0lDLEdBREFDLEVBQVlILEVBR3dCLFFBQWpDRSxFQUFVRSxFQUFRQyxLQUFLTCxLQUFnQixDQUM1QyxHQUFJTSxHQUFVSixFQUFRLEdBQ2xCSyxFQUFNTCxFQUFRLEVBQ2xCQyxHQUFZSCxFQUFJUSxRQUFRRixFQUFTTCxFQUFPTSxJQUcxQyxNQUFPSixHQTdCVCxHQUFJTSxNQUNBQyxFQUFXLE9BQ1hDLEdBQVcsTUFBTyxPQUFRLE1BQU8sVUFHakNQLEVBQVUsY0FxRGQsT0ExQkFPLEdBQVFDLFFBQVEsU0FBVUMsR0FDeEJKLEVBQVVJLEdBQVUsU0FBVUMsR0FxQjVCLE1BcEJJQSxHQUFRYixTQUNWYSxFQUFRZCxJQUFNRCxFQUFXZSxFQUFRZCxJQUFLYyxFQUFRYixTQU01Q2EsRUFBUUMsT0FBU0QsRUFBUUUsZUFDM0JGLEVBQVFDLEtBQU9FLEtBQUtDLFVBQVVKLEVBQVFDLE9BR3hDRCxFQUFVSyxPQUFPQyxRQUNmUCxPQUFRQSxFQUNSUSxZQUFhLG1CQUNiQyxTQUFVLE9BQ1ZDLFFBQVMsS0FDUlQsR0FDRGQsSUFBS1UsRUFBV0ksRUFBUWQsTUFHbkJ3QixFQUFFQyxLQUFLWCxNQUlYTCIsImZpbGUiOiJodHRwLmpzIiwic291cmNlc0NvbnRlbnQiOlsidmFyIGh0dHAgPSAoZnVuY3Rpb24gKCkge1xuICAndXNlIHN0cmljdCc7XG5cbiAgdmFyIHB1YmxpY0FwaSA9IHt9O1xuICB2YXIgQkFTRV9VUkwgPSAnL2FwaSc7XG4gIHZhciBtZXRob2RzID0gWydnZXQnLCAncG9zdCcsICdwdXQnLCAnZGVsZXRlJ107XG5cbiAgLy8gdXJsIGNvbnZlcnQg7JeQ7IScIHtkeW5hbWljIHN0cmluZ30g7J2EIO2MkOuzhO2VoCDsoJXqt5zsi51cbiAgdmFyIHJlUGFyYW0gPSAvKFxceyhcXHcrKVxcfSkvZztcblxuICAvKipcbiAgICogdXJsIHBhdGgg64K0IHtkeW5hbWljIHN0cmluZ30g7J2EIHBhcmFtcyDrgrTsl5DshJwga2V5IOywuOyhsOulvCDthrXtlbQg7LC+7JWE7IScIOuwlOq/lOykgOuLpC5cbiAgICpcbiAgICogZXhhbXBsZTpcbiAgICogIHVybDogJy9jb25mZXJlbmNlcy97aWR9JywgcGFyYW1zOiB7aWQ6IDF9XG4gICAqICAgIC0+IGNvbnZlcnRlZDogJy9jb25mZXJlbmNlcy8xJ1xuICAgKlxuICAgKiAgdXJsOiAnL3t0eXBlfS91cGxvYWRGaWxlJywgcGFyYW1zOiB7dHlwZTogJ3Nlc3Npb25zJ31cbiAgICogICAgLT4gY29udmVydGVkOiAnL3Nlc3Npb25zL3VwbG9hZEZpbGUnXG4gICAqXG4gICAqIEByZXR1cm4gY29udmVydGVkIHtzdHJpbmd9IOyCrOyaqeyekOqwgCDrhJjqsqjspIAgcGFyYW1ldGVyIG1hcHBpbmcg7J2EIOyZhOujjO2VnCB1cmxcbiAgICovXG4gIGZ1bmN0aW9uIGNvbnZlcnRVcmwgKHVybCwgcGFyYW1zKSB7XG4gICAgdmFyIGNvbnZlcnRlZCA9IHVybDtcbiAgICB2YXIgbWF0Y2hlcztcblxuICAgIHdoaWxlKChtYXRjaGVzID0gcmVQYXJhbS5leGVjKHVybCkpICE9PSBudWxsKSB7XG4gICAgICB2YXIgbWF0Y2hlZCA9IG1hdGNoZXNbMV07XG4gICAgICB2YXIga2V5ID0gbWF0Y2hlc1syXTtcbiAgICAgIGNvbnZlcnRlZCA9IHVybC5yZXBsYWNlKG1hdGNoZWQsIHBhcmFtc1trZXldKTtcbiAgICB9XG5cbiAgICByZXR1cm4gY29udmVydGVkO1xuICB9XG5cbiAgbWV0aG9kcy5mb3JFYWNoKGZ1bmN0aW9uIChtZXRob2QpIHtcbiAgICBwdWJsaWNBcGlbbWV0aG9kXSA9IGZ1bmN0aW9uIChvcHRpb25zKSB7XG4gICAgICBpZiAob3B0aW9ucy5wYXJhbXMpIHtcbiAgICAgICAgb3B0aW9ucy51cmwgPSBjb252ZXJ0VXJsKG9wdGlvbnMudXJsLCBvcHRpb25zLnBhcmFtcyk7XG4gICAgICB9XG4gICAgICAvLyBUT0RPOiBvcHRpb25zIOqwneyytOyXkCBzZWFyY2gg7KCV67O06rCAIOuTpOyWtOyYpOuptCwgdXJsIHF1ZXJ5IHN0cmluZyDsnLzroZwg67OA6rK9XG4gICAgICAvLyBpZiAob3B0aW9ucy5zZWFyY2gpIHtcbiAgICAgIC8vICAgb3B0aW9ucy51cmwgPSBvcHRpb25zLnVybCArICQucGFyYW1zKG9wdGlvbnMuc2VhcmNoKTtcbiAgICAgIC8vIH1cbiAgICAgIGlmIChvcHRpb25zLmRhdGEgJiYgIW9wdGlvbnMuaXNGaWxlU2VydmVyKSB7XG4gICAgICAgIG9wdGlvbnMuZGF0YSA9IEpTT04uc3RyaW5naWZ5KG9wdGlvbnMuZGF0YSlcbiAgICAgIH1cblxuICAgICAgb3B0aW9ucyA9IE9iamVjdC5hc3NpZ24oe1xuICAgICAgICBtZXRob2Q6IG1ldGhvZCxcbiAgICAgICAgY29udGVudFR5cGU6ICdhcHBsaWNhdGlvbi9qc29uJyxcbiAgICAgICAgZGF0YVR5cGU6ICdqc29uJyxcbiAgICAgICAgdGltZW91dDogNTAwMFxuICAgICAgfSwgb3B0aW9ucywge1xuICAgICAgICB1cmw6IEJBU0VfVVJMICsgb3B0aW9ucy51cmxcbiAgICAgIH0pO1xuXG4gICAgICByZXR1cm4gJC5hamF4KG9wdGlvbnMpO1xuICAgIH07XG4gIH0pO1xuXG4gIHJldHVybiBwdWJsaWNBcGk7XG59KSgpO1xuIl19
