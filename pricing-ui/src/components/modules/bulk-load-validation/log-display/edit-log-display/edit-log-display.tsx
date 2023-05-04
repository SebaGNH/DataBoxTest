import React, { useState, useEffect, useContext } from 'react';
import Modal from '@mui/material/Modal';
import { Box, Button, FormControl, FormControlLabel, Stack, FormLabel, TextField, Typography, Autocomplete, Radio, RadioGroup } from '@mui/material';
import { useTranslation } from 'react-i18next';
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { AdapterMoment } from '@mui/x-date-pickers/AdapterMoment';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import moment, { Moment } from 'moment';
import './edit-log-display.scss';
import { useQuery, useMutation } from 'react-query';
import { currentPricesService } from '../../../../../services/current-prices.service';
import { bulkLoadService } from '../../../../../services/bulk-load.service';
import { AppContext } from '../../../../../App.context';
import { appReducerCases } from '../../../../../reducers/types';
import { Resource, Modality } from '../../../../../models/resource.model';
import Divider from '@mui/material/Divider';
import formatNumber from '../../../../shared/FormatPrice';

const EditLogDisplay = ({ isOpen, onCancel, item }: any) => {
    const { t } = useTranslation();
    const [career, setCareer] = useState<any>(null);
    const [cau, setCau] = useState<any>(null);
    const [period, setPeriod] = useState<any>(null);
    const [modality, setModality] = useState<any>(null);
    const [arancel, setArancel] = useState<any>(null);
    const [turno, setTurno] = useState<any>(null);
    const [price, setPrice] = useState<any>('');
    const [errorPrice, setErrorPrice] = useState(false);
    const [countCareer, setCountCareer] = useState<any>('');
    const [errorCountCareer, setErrorCountCareer] = useState(false);
    const [ticket, setTicket] = useState<any>(null);
    const studentTypesOptions = [
        { value: 'I', label: t('formFilter.studentEntrance') },
        { value: 'RI', label: t('formFilter.studentReEntering') }
    ];
    const [studentType, setStudentType] = useState<any>(item.tipoALumno);
    const [startDate, setStartDate] = useState<any>(moment(item.fechaInicio));
    const [endDate, setEndDate] = useState<any>(moment(item.fechaCarga));
    const [errorStartDate, setErrorStartDate] = useState<any>(null);
    const [errorEndDate, setErrorEndDate] = useState<any>(null);
    const [errorMessageStartDate, setErrorMessageStartDate] = useState('');
    const [errorMessageEndDate, setErrorMessageEndDate] = useState('');
    const [valid, setValid] = useState<boolean>(true);
    const appContext = useContext(AppContext);

    const { data: careersData } = useQuery('careers', currentPricesService.getCareers, {
        onSuccess: () => {
            setCareer({ id: item.idCarrera.toString(), description: item.descripcionCarrera });
            setPrice(item.precio);
            setCountCareer(item.cantidadMaterias);
        },
        refetchOnWindowFocus: false
    });
    const { data: causData } = useQuery('caus', currentPricesService.getCaus, {
        onSuccess: () => {
            setCau({ id: item.idCau.toString(), description: item.cau });
        },
        refetchOnWindowFocus: false
    });
    const { data: modalitiesData } = useQuery('modalities', currentPricesService.getModalities, {
        onSuccess: () => {
            setModality({
                id: item.idTipoModalidad.toString(),
                description: modalitiesData.find((p: Modality) => p.id.toString() === item.idTipoModalidad.toString())?.description,
                modalidad: item.modality
            });
        },
        refetchOnWindowFocus: false
    });
    const { data: periodsData } = useQuery(
        'periods',
        async () => {
            const data = await currentPricesService.getPeriods();
            return data?.map((item: any) => ({ id: item.id, description: `${item.id} - ${item.description}` }));
        },
        {
            onSuccess: () => {
                setPeriod({
                    id: item.turno.toString(),
                    description: periodsData.find((p: Resource) => p.id.toString() === item.turno.toString())?.description
                });
            },
            refetchOnWindowFocus: false
        }
    );
    const { data: arancelsData } = useQuery('arancels', currentPricesService.getArancels, {
        onSuccess: () => {
            setArancel({ id: item.tipoArancelId, description: item.tipoArancel });
        },
        refetchOnWindowFocus: false
    });
    const { data: ticketsData } = useQuery('tickets', currentPricesService.getTickets, {
        onSuccess: () => {
            setTicket({ id: item.idTipoTicket, description: item.typeTicket });
        },
        refetchOnWindowFocus: false
    });
    const { data: turnosData } = useQuery('turno', currentPricesService.getTurns, {
        onSuccess: () => {
            setTurno({ id: item.turnoCursado, description: item.turnoCursadoDesc });
        },
        refetchOnWindowFocus: false
    });
    const editLogCall = async () => {
        const editValues = {
            id: item.idArticle,
            idProgram: career.id,
            descriptionProgram: career.description,
            modality: modality.modalidad,
            typeModality: modality.description.substring(0, 30),
            idTypeModality: modality.id,
            typeTicket: ticket.description,
            typeArancel: arancel.description,
            idTypeArancel: arancel.id,
            turn: period.id,
            cau: cau.description,
            idCau: cau.id,
            academicPeriod: period.description.substring(period.description.indexOf('-') + 2, period.description.indexOf('-') + 12),
            amountCourse: countCareer,
            typeStudent: studentType,
            idTypeTicket: ticket.id,
            startDate: startDate.format('YYYY-MM-DD'),
            turnCourse: turno.id,
            price: parseFloat(price.toString().replace(/[^0-9.-]+/g, ''))
        };
        const data = await bulkLoadService.editLog(editValues);
        return data;
    };
    const { mutate: editLog } = useMutation(editLogCall, {
        onSuccess: () => {
            appContext.dispatch({
                type: appReducerCases.setSnackbarMessage,
                payload: `${t('messages.editUpdateOK')}`
            });
            appContext.dispatch({
                type: appReducerCases.setSnackbarSeverity,
                payload: 'success'
            });
            appContext.dispatch({
                type: appReducerCases.openSnackbar,
                payload: true
            });
        },
        onError: () => {
            appContext.dispatch({
                type: appReducerCases.setSnackbarMessage,
                payload: `${t('messages.editUpdateKO')}`
            });
            appContext.dispatch({
                type: appReducerCases.setSnackbarSeverity,
                payload: 'error'
            });
            appContext.dispatch({
                type: appReducerCases.openSnackbar,
                payload: true
            });
        }
    });
    const handleEditLog = () => {
        editLog();
    };
    const errorMessages = (errotType: string) => {
        switch (errotType) {
            case 'maxDate':
                return {
                    msg: 'La fecha debe ser menor al 31/12/2050',
                    valid: false
                };
            case 'minDate':
                return {
                    msg: 'La fecha debe ser mayor al 01/01/2000',
                    valid: false
                };
            case 'invalidDate':
                return {
                    msg: 'Fecha Inválida',
                    valid: false
                };
            case 'startGreaterThanEnd':
                return {
                    msg: 'La fecha inicial no puede ser mayor a la fecha final',
                    valid: false
                };
            case 'endLessThanStart':
                return {
                    msg: 'La fecha final no puede ser menor a la fecha inicial',
                    valid: false
                };
            default:
                return {
                    msg: '',
                    valid: true
                };
        }
    };
    useEffect(() => {
        const { msg, valid } = errorMessages(errorStartDate);
        setErrorMessageStartDate(msg);
        setValid(valid);
    }, [errorStartDate]);
    useEffect(() => {
        const { msg, valid } = errorMessages(errorEndDate);
        setErrorMessageEndDate(msg);
        setValid(valid);
    }, [errorEndDate]);
    const handleChangeStartDate = (startDate: Moment) => {
        if (startDate?.isValid() && endDate) {
            if (startDate.isAfter(endDate)) {
                setErrorStartDate('startGreaterThanEnd');
            } else {
                setErrorStartDate('');
                setErrorEndDate('');
            }
        }
        setStartDate(startDate);
    };
    const handleChangeEndDate = (endDate: Moment) => {
        if (endDate?.isValid() && startDate) {
            if (endDate.isBefore(startDate)) {
                setErrorEndDate('endLessThanStart');
            } else {
                setErrorStartDate('');
                setErrorEndDate('');
            }
        }
        setEndDate(endDate);
    };
    const handleSetStrudentType = (event: any) => {
        setStudentType(event.target.value);
    };
    const handlePrice = (event: any) => {
        try {
            const value = parseFloat(event.target.value?.replace(/[^0-9.-]+/g, ''));
            if (!isNaN(value)) {
                setErrorPrice(false);
                setValid(true);
            } else {
                setErrorPrice(true);
                setValid(false);
            }
            setPrice(value);
        } catch (error) {
            setErrorPrice(true);
            setValid(false);
        }
    };
    const handleCountCareer = (event: any) => {
        const value = event.target.value;
        if (value !== '' && value >= 0 && value <= 6) {
            setErrorCountCareer(false);
            setValid(true);
        } else {
            setErrorCountCareer(true);
            setValid(false);
        }
        setCountCareer(value);
    };

    return (
        <div>
            <Modal open={isOpen} key={item.id} onClose={onCancel} aria-labelledby="modal-title" aria-describedby="modal-description">
                <Box
                    sx={{
                        position: 'absolute',
                        top: '50%',
                        left: '50%',
                        transform: 'translate(-50%, -50%)',
                        width: 550,
                        bgcolor: 'background.paper',
                        boxShadow: 24,
                        borderRadius: 2,
                        p: 4
                    }}
                >
                    <Typography className="edit_log_title" id="modal-title" variant="h6" component="h2" gutterBottom>
                        {t('formEdit.title')}
                    </Typography>
                    <Divider style={{ color: 'primary', marginBottom: '30px' }} />
                    <Typography id="modal-description" variant="body1" gutterBottom>
                        <div>
                            <div className="edit_log__line edit_log__line_20_80">
                                <FormControl size="small" className="form__control">
                                    <TextField
                                        className="filter__textfield"
                                        size="small"
                                        label={t('formFilter.id')}
                                        value={item.csvId}
                                        variant="outlined"
                                        disabled
                                        type="text"
                                        inputProps={{ tabIndex: 0, pattern: '[0-9]*' }}
                                    />
                                </FormControl>
                                <FormControl size="small" className="form__control formCourse__matAutocomplete" id="rmt-Career">
                                    <Autocomplete
                                        openText=""
                                        value={career}
                                        options={careersData || []}
                                        onChange={(e: any, newValue: any | null) => setCareer(newValue)}
                                        disablePortal
                                        noOptionsText={t('labels.noOptionsText')}
                                        isOptionEqualToValue={(option, value) => option.id === value.id}
                                        getOptionLabel={(option) => option?.description}
                                        renderInput={(params) => (
                                            <TextField key={params.id} value={params.id} {...params} label={`${t('formFilter.career')}`} />
                                        )}
                                        renderOption={(props, option: any) => (
                                            <li {...props} value={option?.id} key={option?.id}>
                                                {option?.description}
                                            </li>
                                        )}
                                    />
                                </FormControl>
                            </div>
                            <div className="edit_log__line edit_log__line_30_70">
                                <FormControl size="small" className="form__control formCourse__matAutocomplete" id="rmt-Cau">
                                    <Autocomplete
                                        clearText={'Limpiar'}
                                        openText=""
                                        value={cau}
                                        options={causData || []}
                                        onChange={(e: any, newValue: any | null) => setCau(newValue)}
                                        disablePortal
                                        noOptionsText={t('labels.noOptionsText')}
                                        isOptionEqualToValue={(option, value) => option.id === value.id}
                                        getOptionLabel={(option) => option?.description}
                                        renderInput={(params) => (
                                            <TextField key={params.id} value={params.id} {...params} label={`${t('formFilter.cau')}`} />
                                        )}
                                        renderOption={(props, option: any) => (
                                            <li {...props} value={option?.id} key={option?.id}>
                                                {option?.description}
                                            </li>
                                        )}
                                    />
                                </FormControl>
                                <FormControl size="small" className="form__control formCourse__matAutocomplete" id="rmt-Modality">
                                    <Autocomplete
                                        clearText={'Limpiar'}
                                        openText=""
                                        value={modality}
                                        options={modalitiesData || []}
                                        onChange={(e: any, newValue: any | null) => setModality(newValue)}
                                        disablePortal
                                        noOptionsText={t('labels.noOptionsText')}
                                        isOptionEqualToValue={(option, value) => option.id === value.id}
                                        getOptionLabel={(option) => option?.description}
                                        renderInput={(params) => (
                                            <TextField key={params.id} value={params.id} {...params} label={`${t('formFilter.modality')}`} />
                                        )}
                                        renderOption={(props, option: any) => (
                                            <li {...props} value={option?.id} key={option?.id}>
                                                {option?.description}
                                            </li>
                                        )}
                                    />
                                </FormControl>
                            </div>
                            <div className="edit_log__line edit_log__line_1">
                                <FormControl size="small" className="form__control formCourse__matAutocomplete" id="rmt-Period">
                                    <Autocomplete
                                        clearText={'Limpiar'}
                                        openText=""
                                        value={period}
                                        options={periodsData || []}
                                        onChange={(e: any, newValue: any | null) => setPeriod(newValue)}
                                        disablePortal
                                        noOptionsText={t('labels.noOptionsText')}
                                        isOptionEqualToValue={(option, value) => option.id === value.id}
                                        getOptionLabel={(option) => option?.description}
                                        renderInput={(params) => (
                                            <TextField key={params.id} value={params.id} {...params} label={`${t('formFilter.period')}`} />
                                        )}
                                        renderOption={(props, option: any) => (
                                            <li {...props} value={option?.id} key={option?.id}>
                                                {option?.description}
                                            </li>
                                        )}
                                    />
                                </FormControl>
                            </div>
                            <div className="edit_log__line edit_log__line_3">
                                <FormControl size="small" className="form__control formCourse__matAutocomplete" id="rmt-Arancel">
                                    <Autocomplete
                                        clearText={'Limpiar'}
                                        openText=""
                                        value={arancel}
                                        options={arancelsData || []}
                                        onChange={(e: any, newValue: any | null) => setArancel(newValue)}
                                        disablePortal
                                        noOptionsText={t('labels.noOptionsText')}
                                        isOptionEqualToValue={(option, value) => option.id === value.id}
                                        getOptionLabel={(option) => option?.description}
                                        renderInput={(params) => (
                                            <TextField key={params.id} value={params.id} {...params} label={`${t('formFilter.tariff')}`} />
                                        )}
                                        renderOption={(props, option: any) => (
                                            <li {...props} value={option?.id} key={option?.id}>
                                                {option?.description}
                                            </li>
                                        )}
                                    />
                                </FormControl>
                                <FormControl size="small" className="form__control formCourse__matAutocomplete" id="rmt-Ticket">
                                    <Autocomplete
                                        clearText={'Limpiar'}
                                        openText=""
                                        value={ticket}
                                        options={ticketsData || []}
                                        onChange={(e: any, newValue: any | null) => setTicket(newValue)}
                                        disablePortal
                                        noOptionsText={t('labels.noOptionsText')}
                                        isOptionEqualToValue={(option, value) => option.id === value.id}
                                        getOptionLabel={(option) => option?.description}
                                        renderInput={(params) => (
                                            <TextField key={params.id} value={params.id} {...params} label={`${t('formFilter.ticket')}`} />
                                        )}
                                        renderOption={(props, option: any) => (
                                            <li {...props} value={option?.id} key={option?.id}>
                                                {option?.description}
                                            </li>
                                        )}
                                    />
                                </FormControl>
                                <FormControl size="small" className="form__control formCourse__matAutocomplete" id="rmt-Cursando">
                                    <Autocomplete
                                        clearText={'Limpiar'}
                                        openText=""
                                        value={turno}
                                        options={turnosData || []}
                                        onChange={(e: any, newValue: any | null) => setTurno(newValue)}
                                        disablePortal
                                        noOptionsText={t('labels.noOptionsText')}
                                        isOptionEqualToValue={(option, value) => option.id === value.id}
                                        getOptionLabel={(option) => option?.description}
                                        renderInput={(params) => (
                                            <TextField key={params.id} value={params.id} {...params} label={`${t('formFilter.cursando')}`} />
                                        )}
                                        renderOption={(props, option: any) => (
                                            <li {...props} value={option?.id} key={option?.id}>
                                                {option?.description}
                                            </li>
                                        )}
                                    />
                                </FormControl>
                            </div>
                            <div className="edit_log__line edit_log__line_3">
                                <FormControl size="small" className="form__control">
                                    <TextField
                                        size="small"
                                        label={t('formEdit.price')}
                                        variant="outlined"
                                        type="text"
                                        value={formatNumber(price)}
                                        onChange={handlePrice}
                                        error={errorPrice}
                                        InputProps={{
                                            style: { color: 'black' }
                                        }}
                                    />
                                </FormControl>
                                <FormControl size="small" className="form__control">
                                    <TextField
                                        size="small"
                                        label={t('formEdit.count')}
                                        variant="outlined"
                                        onChange={handleCountCareer}
                                        type="number"
                                        value={countCareer}
                                        error={errorCountCareer}
                                        InputProps={{
                                            style: { color: 'black' },
                                            inputProps: { min: 0, max: 6 }
                                        }}
                                    />
                                </FormControl>
                                <FormControl size="small" className="form__control">
                                    <LocalizationProvider dateAdapter={AdapterMoment}>
                                        <DemoContainer components={['DatePicker']}>
                                            <DatePicker
                                                format="DD/MM/YYYY"
                                                label={t('labels.dateLoad')}
                                                slotProps={{
                                                    textField: { size: 'small', helperText: errorMessageEndDate },
                                                    actionBar: {
                                                        actions: ['clear']
                                                    }
                                                }}
                                                disabled
                                                onError={(newError) => setErrorEndDate(newError)}
                                                value={endDate}
                                                onChange={(newValue) => handleChangeEndDate(newValue)}
                                                minDate={moment('2000-01-01T00:00:00.000')}
                                                maxDate={moment('2050-12-31T23:59:59.999')}
                                            />
                                        </DemoContainer>
                                    </LocalizationProvider>
                                </FormControl>
                            </div>
                            <div className="edit_log__line edit_log__line_1">
                                <FormControl size="small" className="form__control">
                                    <LocalizationProvider dateAdapter={AdapterMoment}>
                                        <DemoContainer components={['DatePicker']}>
                                            <DatePicker
                                                format="DD/MM/YYYY"
                                                label={t('labels.dateInit')}
                                                slotProps={{
                                                    textField: { size: 'small', helperText: errorMessageStartDate },
                                                    actionBar: {
                                                        actions: ['clear']
                                                    }
                                                }}
                                                onError={(newError) => setErrorStartDate(newError)}
                                                value={startDate}
                                                onChange={(newValue) => handleChangeStartDate(newValue)}
                                                minDate={moment('2000-01-01T00:00:00.000')}
                                                maxDate={moment('2050-12-31T23:59:59.999')}
                                            />
                                        </DemoContainer>
                                    </LocalizationProvider>
                                </FormControl>
                            </div>
                            <div className="edit_log__line">
                                <FormControl size="small" id="rmt-TypeStudent">
                                    <FormLabel className="edit_Display_TypeStudent">{t('formFilter.typeStudent')}</FormLabel>
                                    <RadioGroup
                                        className="edit_Display_TypeStudent_options"
                                        defaultValue={item.tipoALumno}
                                        name="radio-buttons-group"
                                        onChange={handleSetStrudentType}
                                    >
                                        {studentTypesOptions.map((item: any) => (
                                            <FormControlLabel
                                                value={item.value}
                                                key={item.id}
                                                control={<Radio />}
                                                label={item.label}
                                                style={{ marginBottom: '-15px' }}
                                            />
                                        ))}
                                    </RadioGroup>
                                </FormControl>
                            </div>
                        </div>
                    </Typography>
                    <Stack className="edit_Display_Button" direction="row" spacing={2}>
                        <Button color="error" variant="contained" onClick={onCancel}>
                            Cancelar
                        </Button>
                        <Button variant="contained" onClick={handleEditLog} disabled={!valid}>
                            Confirmar
                        </Button>
                    </Stack>
                </Box>
            </Modal>
        </div>
    );
};

export default EditLogDisplay;
