// Best-effort print prevention
window.addEventListener("keydown", function (e) {
  const isMac = navigator.platform.toUpperCase().indexOf("MAC") >= 0;
  const ctrlOrCmd = isMac ? e.metaKey : e.ctrlKey;

  if (ctrlOrCmd && (e.key === "p" || e.key === "P")) {
    e.preventDefault();
    alert("Printing is disabled on this secured page.");
    return false;
  }
});

// Block print from menu where possible
window.onbeforeprint = function () {
  document.body.innerHTML = "<h2>Printing is disabled on this secured page.</h2>";
};
