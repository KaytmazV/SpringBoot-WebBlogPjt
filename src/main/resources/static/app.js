const API_URL = 'http://localhost:8096/api';

function RegisterForm({ setIsLoggedIn }) {
    const [username, setUsername] = React.useState('');
    const [password, setPassword] = React.useState('');
    const [error, setError] = React.useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        
        try {
            const response = await fetch(`${API_URL}/auth/register`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json',
                },
                credentials: 'include',
                body: JSON.stringify({ username, password }),
            });

            const data = await response.text();
            
            if (response.ok) {
                alert('Kayıt başarılı!');
                setIsLoggedIn(true);
            } else {
                setError(data || 'Kayıt işlemi başarısız oldu.');
                alert(data || 'Kayıt işlemi başarısız oldu.');
            }
        } catch (error) {
            console.error('Kayıt hatası:', error);
            setError('Bir hata oluştu. Lütfen daha sonra tekrar deneyin.');
            alert('Bir hata oluştu. Lütfen daha sonra tekrar deneyin.');
        }
    };

    return (
        <div className="register-container">
            <form onSubmit={handleSubmit} className="register-form">
                <h2>Kayıt Ol</h2>
                {error && <div className="error-message">{error}</div>}
                <div className="form-group">
                    <input
                        type="text"
                        placeholder="Kullanıcı Adı"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <input
                        type="password"
                        placeholder="Şifre"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Kayıt Ol</button>
            </form>
        </div>
    );
}

