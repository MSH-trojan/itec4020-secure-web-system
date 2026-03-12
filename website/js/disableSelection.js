console.log("Security script loaded");

// Disable text selection
document.addEventListener("selectstart", function (e) {
  e.preventDefault();
});

// Disable key shortcuts
document.addEventListener("keydown", function (e) {

  const key = (e.key || "").toLowerCase();

  // Block Ctrl+C and Ctrl+A
  if (e.ctrlKey && (key === "c" || key === "a")) {
    e.preventDefault();
    return false;
  }

  // Block View Source (Ctrl+U)
  if (e.ctrlKey && key === "u") {
    e.preventDefault();
    return false;
  }

  // Block Save Page (Ctrl+S)
  if (e.ctrlKey && key === "s") {
    e.preventDefault();
    return false;
  }

  // Block DevTools (F12)
  if (key === "f12") {
    e.preventDefault();
    return false;
  }

  // Block DevTools shortcuts (Ctrl+Shift+I/J/C)
  if (e.ctrlKey && e.shiftKey && (key === "i" || key === "j" || key === "c")) {
    e.preventDefault();
    return false;
  }
});
