function disableDragging() {
  document.addEventListener("dragstart", function (event) {
    event.preventDefault();
  });
}

disableDragging();
