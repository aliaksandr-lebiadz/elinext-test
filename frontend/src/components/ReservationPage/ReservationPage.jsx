import React from 'react';
import PropTypes from 'prop-types';
import './ReservationPage.scss';

const ReservationPage = ({ token }) => {
    return <h1>{token}</h1>;
};

ReservationPage.propTypes = {
    token: PropTypes.string
};

export default ReservationPage;