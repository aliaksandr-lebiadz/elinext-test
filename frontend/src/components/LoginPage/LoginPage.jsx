import React, { useReducer, useState } from 'react';
import { login, signUp } from '../../api/requests';
import './LoginPage.scss';

const LoginPage = ({ setToken }) => {
    const initialFields = { login: '', password: '' };
    const [fields, setFields] = useState(initialFields);
    const [error, setError] = useState();
    const [hasAccount, changeHasAccount] = useReducer(hasAccount => { 
        setFields(initialFields);
        setError(null);
        return !hasAccount;
    }, true);

    const handleChange = event => {
        event.persist();
        setFields(prevState => ({
            ...prevState,
            [event.target.name]: event.target.value
        }));
    };

    const handleButtonClick = () => {
        if(fields.login === '' || fields.password === '') {
            setError('Fill the fields!');
            return;
        }
        if(hasAccount) {
            login(fields.login, fields.password)
            .then(data => setToken(data.token))
            .catch(() => setError('Invalid login or password. Try again!'));
        } else {
            signUp(fields.login, fields.password)
            .then(changeHasAccount)
            .catch(() => setError('Login is already taken!'));
        }
    };

    return (
        <div className="login-dialog">
            <div className="login-dialog-header">
                {hasAccount ? 'Login to your account' : 'Create new account'}
            </div>
            <div className="login-dialog-body">
                <label htmlFor="login-input">Login</label>
                <input
                    type="text"
                    name="login"
                    className="login-dialog-input"
                    id="login-input"
                    value={fields.login}
                    onChange={handleChange}
                />
                <label htmlFor="password-input">Password</label>
                <input
                    type="password"
                    name="password"
                    className="login-dialog-input"
                    id="password-input"
                    value={fields.password}
                    onChange={handleChange}
                />
                <div className="login-dialog-error">{error}</div>
                <input className="login-dialog-button" type="button" value={hasAccount ? 'Login' : 'Sign up'} onClick={handleButtonClick}/>
            </div>
            <div className="sign-up-hint">
                {hasAccount ? `Don't have an account?` : 'Already have an account?'}
                <span className="sign-up-hint__link" onClick={changeHasAccount}>
                    {hasAccount ? 'Sign up' : 'Login'}
                </span>
            </div>
        </div>
    );
};

export default LoginPage;