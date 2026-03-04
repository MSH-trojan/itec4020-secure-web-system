document.addEventListener("selectstart", function (e) {
  e.preventDefault();
});

document.addEventListener("keydown", function (e) {
  if (e.ctrlKey && (e.key === "c" || e.key === "a")) {
    e.preventDefault();
  }
});
