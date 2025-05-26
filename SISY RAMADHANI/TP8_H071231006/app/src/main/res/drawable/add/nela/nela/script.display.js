        // Ambil data dari localStorage
        const fullName = localStorage.getItem('fullName') || "Tidak Diketahui";
        const email = localStorage.getItem('email') || "Tidak Diketahui";
        const birthDate = localStorage.getItem('birthDate') || "Tidak Diketahui";
        const gender = localStorage.getItem('gender') || "Tidak Diketahui";

        // Tampilkan data pada elemen yang sesuai
        document.getElementById('fullName').innerText = fullName;
        document.getElementById('email').innerText = email;
        document.getElementById('birthDate').innerText = birthDate;
        document.getElementById('gender').innerText = gender;