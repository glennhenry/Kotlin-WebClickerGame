function validateForm(event) {
  const username = document.getElementById("username-field").value.trim();
  const password = document.getElementById("password-field").value.trim();

  // non-empty letters + numbers
  const regex = /^[A-Za-z0-9]+$/;

  if (!username || !password) {
    alert("Both fields are required.");
    event.preventDefault();
    return false;
  }
  if (!regex.test(username) || !regex.test(password)) {
    alert("Username and password must contain only letters and numbers.");
    event.preventDefault();
    return false;
  }

  return true;
}
