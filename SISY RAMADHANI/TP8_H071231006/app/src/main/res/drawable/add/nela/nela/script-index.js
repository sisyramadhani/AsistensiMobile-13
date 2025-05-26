const toggleSwitch = document.getElementById('toggleSwitch');
const statusText = document.getElementById('statusText');

toggleSwitch.addEventListener('change', function() {
    if (this.checked) {
        statusText.textContent = 'ON';
    } else {
        statusText.textContent = 'OFF';
    }
});

document.getElementById('daftar-posisi').onclick = function() {
    // Ambil nilai dari input
    const fullName = document.getElementById('fullNameInput').value.trim();
    const email = document.getElementById('emailInput').value.trim();
    const birthDate = document.getElementById('birthDateInput').value;
    const gender = document.querySelector('input[name="gender"]:checked');

    // Reset pesan kesalahan
    document.querySelectorAll('.error-message').forEach(el => el.remove());

    // Validasi
    let isValid = true;
    if (!fullName) {
        showError('fullNameInput', 'Field ini harus diisi.');
        isValid = false;
    }
    if (!email) {
        showError('emailInput', 'Field ini harus diisi.');
        isValid = false;
    }
    if (!birthDate) {
        showError('birthDateInput', 'Field ini harus diisi.');
        isValid = false;
    }
    if (!gender) {
        alert('Pilih jenis kelamin.');
        isValid = false;
    }
    if (!document.getElementById('agreementCheckbox').checked) {
        alert('Anda harus menyetujui syarat dan ketentuan.');
        isValid = false;
    }

    // Validasi status toggle
    if (!toggleSwitch.checked) {
        alert('Perhatian: Status toggle harus ON untuk melanjutkan pendaftaran. Harap aktifkan status dan coba lagi.');
        isValid = false;
    }

    // Jika semua validasi berhasil
    if (isValid) {
        // Simpan ke localStorage
        localStorage.setItem('fullName', fullName);
        localStorage.setItem('email', email);
        localStorage.setItem('birthDate', birthDate);
        localStorage.setItem('gender', gender.id);

        // Arahkan ke halaman kedua
        window.location.href = 'display.html';
    }
};

function showError(inputId, message) {
    const inputElement = document.getElementById(inputId);
    const errorMessage = document.createElement('span');
    errorMessage.className = 'error-message';
    errorMessage.style.color = 'red';
    errorMessage.textContent = message;
    inputElement.parentNode.insertBefore(errorMessage, inputElement.nextSibling);
}