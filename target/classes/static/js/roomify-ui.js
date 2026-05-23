(function () {
  const footerHtml = `
    <div class="container roomify-footer">
      <div class="roomify-footer-grid">
        <div>
          <h3>Roomify</h3>
          <p>Premium hotel discovery, smooth booking, and comfortable stays shaped into one polished experience.</p>
        </div>
        <div>
          <h5>Explore</h5>
          <a href="/">Home</a>
          <a href="/hotel/searchPage">Search Hotels</a>
          <a href="/booking/myBooking">My Bookings</a>
        </div>
        <div>
          <h5>Account</h5>
          <a href="/login">Login</a>
          <a href="/signup">Create Account</a>
          <a href="/ourProfile">Profile</a>
        </div>
        <div>
          <h5>Experience</h5>
          <p>Responsive UI, refined layouts, premium cards, clean forms, and mobile-ready booking flows.</p>
        </div>
      </div>
      <div class="roomify-footer-bottom">
        <span>© 2026 Roomify. All rights reserved.</span>
        <strong>Developed by Satyam Singh</strong>
      </div>
    </div>`;

  document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll("footer, .footer").forEach(function (footer) {
      if (!footer.classList.contains("roomify-enhanced")) {
        footer.classList.add("roomify-enhanced");
        footer.innerHTML = footerHtml;
      }
    });

    document.querySelectorAll("table").forEach(function (table) {
      if (!table.parentElement.classList.contains("table-responsive") &&
          !table.parentElement.classList.contains("table-container")) {
        const wrapper = document.createElement("div");
        wrapper.className = "table-responsive";
        table.parentNode.insertBefore(wrapper, table);
        wrapper.appendChild(table);
      }
    });

    document.querySelectorAll(".card, .card-box, .hotel-card, .room-card, .booking-card, .profile-card, .login-card, .signup-card, .form-card").forEach(function (el, index) {
      el.classList.add("rf-fade-up");
      el.style.animationDelay = Math.min(index * 35, 280) + "ms";
    });
  });
})();
