function clicked(point) {
    const container = document.querySelector('.main-content');
    const el = document.createElement('p');
    el.className = 'clicked-text';
    el.style.top = (Math.random() * 50) + 'px';
    el.style.right = (Math.random() * 50) + 'px';
    el.textContent = `+${point} click!`;
    container.appendChild(el);

    setTimeout(() => el.remove(), 1000);
}
