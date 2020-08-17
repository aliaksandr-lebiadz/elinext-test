import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import { Select, DatePicker, Input } from 'antd';
import { getRoomTypes, getRoomsByType, addReservation } from '../../api/requests';
import './ReservationPage.scss';
import 'antd/dist/antd.css';

const { Option } = Select;
const { RangePicker } = DatePicker;

const ReservationPage = ({ token }) => {
    const [types, setTypes] = useState([]);
    const [rooms, setRooms] = useState([]);
    const [fields, setFields] = useState({
        type: null,
        name: null,
        description: null,
        room: { id: null, name: null },
        startDate: null,
        endDate: null
    });

    useEffect(() => {
        getRoomTypes(token)
        .then(setTypes)
        .catch(console.error);
    }, [token]);

    useEffect(() => {
        if(fields.type) {
            getRoomsByType(fields.type, token)
            .then(setRooms)
            .catch(console.error);
        }
    }, [fields.type, token]);

    const handleButtonClick = event => {
        event.persist();
        //there should be fields validation (non null and dates intersections)
        const reservation = {
            name: fields.name,
            description: fields.description,
            roomId: fields.room.id,
            startDate: fields.startDate.format("YYYY-MM-DDTHH:mm:ss"),
            endDate: fields.endDate.format("YYYY-MM-DDTHH:mm:ss")
        };
        addReservation(reservation, token)
        .then(console.log)
        .catch(console.error);
    };

    return (
        <div className="dialog" id="reservation-type-dialog">
            <div className="dialog-header">Create reservation</div>
            <div className="dialog-body" id="reservation-dialog-body">
                <div className="field-row">
                    <span className="room-label">Room type:</span>
                    <Select
                        className="field"
                        onChange={value => setFields(prevState => ({...prevState, type: value}))}
                    >
                        {types.map((type, index) =>
                            <Option key={index} value={type}>{type}</Option>
                        )}
                    </Select>
                </div>
                <div className="field-row">
                    <span className="room-label">Name:</span>
                    <Input
                        className="field"
                        onChange={event => {
                            event.persist();
                            setFields(prevState => ({...prevState, name: event.target.value}));
                        }}
                        style={{ width: 175 }}
                    />
                </div>
                <div className="field-row">
                    <span className="room-label">Description:</span>
                    <Input
                        className="field"
                        onChange={event => {
                            event.persist();
                            setFields(prevState => ({...prevState, description: event.target.value}));
                        }}
                        style={{ width: 175 }}
                    />
                </div>
                <div className="field-row">
                    <span className="room-label">Room:</span>
                    <Select
                        className="field"
                        onChange={id => setFields(prevState => ({...prevState, room: {id, name: rooms.filter(r => r.id === id).name}}))}
                    >
                        {rooms.map(room =>
                            <Option key={room.id} value={room.id}>{room.name}</Option>
                        )}
                    </Select>
                </div>
                <div className="field-row">
                    <span className="room-label">Date interval:</span>
                    <RangePicker
                        className="field"
                        showTime
                        onChange={value => {
                            console.log(value);
                            setFields(prevState => ({...prevState, startDate: value[0]}));
                            setFields(prevState => ({...prevState, endDate: value[1]}));
                        }}
                    />
                </div>
                <input
                    className="dialog-button"
                    id="reservation-dialog-button"
                    type="button"
                    value="Create"
                    onClick={handleButtonClick}
                />
            </div>
        </div>
    );
};

ReservationPage.propTypes = {
    token: PropTypes.string
};

export default ReservationPage;