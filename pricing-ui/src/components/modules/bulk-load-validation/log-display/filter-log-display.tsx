import React, { useState, useEffect } from 'react';
import { FormControl, TextField, Button, Autocomplete, InputLabel, MenuItem, Select } from '@mui/material';
import { useTranslation } from 'react-i18next';
import { Search } from '@mui/icons-material';
import { useQuery } from 'react-query';
import './log-display.scss';
import { currentPricesService } from '../../../../services/current-prices.service';
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { AdapterMoment } from '@mui/x-date-pickers/AdapterMoment';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import moment, { Moment } from 'moment';

interface FilterI {
    handleFilters: any;
    csvId: any;
    clean: boolean;
}

const FilterLogDisplay = ({ handleFilters, csvId, clean }: FilterI) => {
    const { t } = useTranslation();
    const [career, setCareer] = useState<any>(null);
    const [cau, setCau] = useState<any>(null);
    const [period, setPeriod] = useState<any>(null);
    const [modality, setModality] = useState<any>(null);
    const [arancel, setArancel] = useState<any>(null);
    const [turno, setTurno] = useState<any>(null);
    const [ticket, setTicket] = useState<any>(null);
    const [studentType, setStudentType] = useState<any>('');
    const [status, setStatus] = useState<any>(null);
    const studentTypesOptions = [
        { value: 'all', label: t('labels.all') },
        { value: 'I', label: t('formFilter.studentEntrance') },
        { value: 'RI', label: t('formFilter.studentReEntering') }
    ];
    const [startDate, setStartDate] = useState<any>(null);
    const [endDate, setEndDate] = useState<any>(null);
    const [errorStartDate, setErrorStartDate] = useState<any>(null);
    const [errorEndDate, setErrorEndDate] = useState<any>(null);
    const [errorMessageStartDate, setErrorMessageStartDate] = useState('');
    const [errorMessageEndDate, setErrorMessageEndDate] = useState('');
    const [valid, setValid] = useState<boolean>(true);

    const { data: careersData } = useQuery('careers', currentPricesService.getCareers, {
        refetchOnWindowFocus: false
    });
    const { data: causData } = useQuery('caus', currentPricesService.getCaus, {
        refetchOnWindowFocus: false
    });
    const { data: modalitiesData } = useQuery('modalities', currentPricesService.getModalities, {
        refetchOnWindowFocus: false
    });
    const { data: periodsData } = useQuery(
        'periods',
        async () => {
            const data = await currentPricesService.getPeriods();
            return data?.map((item: any) => ({ id: item.id, description: `${item.id} - ${item.description}` }));
        },
        {
            refetchOnWindowFocus: false
        }
    );
    const { data: arancelsData } = useQuery('arancels', currentPricesService.getArancels, {
        refetchOnWindowFocus: false
    });
    const { data: ticketsData } = useQuery('tickets', currentPricesService.getTickets, {
        refetchOnWindowFocus: false
    });

    const { data: turnosData } = useQuery('turno', currentPricesService.getTurns, {
        refetchOnWindowFocus: false
    });

    const { data: statusData } = useQuery('status', currentPricesService.getStatus, {
        refetchOnWindowFocus: false
    });

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

    useEffect(() => {
        if (clean) {
            handleClean();
        }
    }, [clean]);

    const handleSubmit = (e: any) => {
        e.preventDefault();
        handleFilters({
            csvId,
            cauId: cau?.id,
            modalityId: modality?.id,
            careerId: career?.id,
            ticketId: ticket?.id,
            periodId: period?.id,
            turnoId: turno?.id,
            arancelId: arancel?.id,
            studentTypeId: studentType !== 'all' ? studentType : '',
            statusId: status?.id,
            fechaMin: startDate && new Date(startDate.format('YYYY-MM-DD')).toISOString(),
            fechaMax: endDate && new Date(endDate.format('YYYY-MM-DD')).toISOString()
        });
    };

    const handleClean = () => {
        setStudentType('all');
        setCareer(null);
        setCau(null);
        setPeriod(null);
        setStatus(null);
        setModality(null);
        setArancel(null);
        setTicket(null);
        setStartDate(null);
        setEndDate(null);
        setTurno(null);
        setStudentType('');
        handleFilters({
            csvId: '',
            cauId: null,
            status: null,
            modalityId: null,
            careerId: null,
            ticketId: null,
            periodId: null,
            turnoId: null,
            arancelId: null,
            studentTypeId: '',
            statusId: null,
            fechaMin: null,
            fechaMax: null
        });
    };

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

    return (
        <form className="filter__prices" onSubmit={handleSubmit}>
            <div className="filter__controls">
                <div className="filter__line filter__line-custom_prices_4">
                    <FormControl size="small" className="form__control" id="rmt-CsvId">
                        <TextField
                            className="filter__textfield"
                            size="small"
                            label={t('formFilter.id')}
                            variant="outlined"
                            type="text"
                            disabled
                            value={csvId}
                            inputProps={{ tabIndex: 0, pattern: '[0-9]*' }}
                        />
                    </FormControl>
                    <FormControl size="small" className="form__control formCourse__matAutocomplete" id="rmt-Career">
                        <Autocomplete
                            clearText={'Limpiar'}
                            openText=""
                            value={career}
                            options={careersData || []}
                            onChange={(e: any, newValue: any | null) => setCareer(newValue)}
                            disablePortal
                            noOptionsText={t('labels.noOptionsText')}
                            isOptionEqualToValue={(option, value) => option.id === value.id}
                            getOptionLabel={(option) => option?.description}
                            renderInput={(params) => <TextField key={params.id} value={params.id} {...params} label={`${t('formFilter.career')}`} />}
                            renderOption={(props, option: any) => (
                                <li {...props} value={option?.id} key={option?.id}>
                                    {option?.description}
                                </li>
                            )}
                        />
                    </FormControl>
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
                            renderInput={(params) => <TextField key={params.id} value={params.id} {...params} label={`${t('formFilter.cau')}`} />}
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
                <div className="filter__line filter__line-custom_prices_3 mt-1">
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
                            renderInput={(params) => <TextField key={params.id} value={params.id} {...params} label={`${t('formFilter.period')}`} />}
                            renderOption={(props, option: any) => (
                                <li {...props} value={option?.id} key={option?.id}>
                                    {option?.description}
                                </li>
                            )}
                        />
                    </FormControl>
                    <FormControl size="small" className="form__control">
                        <LocalizationProvider dateAdapter={AdapterMoment}>
                            <DemoContainer components={['DatePicker']}>
                                <DatePicker
                                    format="YYYY-MM-DD"
                                    label="Fecha Inicio"
                                    slotProps={{
                                        textField: { size: 'small', helperText: errorMessageStartDate },
                                        actionBar: {
                                            actions: ['clear']
                                        }
                                    }}
                                    onError={(newError) => setErrorStartDate(newError)}
                                    value={startDate}
                                    onChange={(newValue) => handleChangeStartDate(newValue)}
                                    minDate={moment('01/01/2000')}
                                    maxDate={moment('31/12/2050')}
                                />
                            </DemoContainer>
                        </LocalizationProvider>
                    </FormControl>
                    <FormControl size="small" className="form__control">
                        <LocalizationProvider dateAdapter={AdapterMoment}>
                            <DemoContainer components={['DatePicker']}>
                                <DatePicker
                                    format="YYYY-MM-DD"
                                    label="Fecha Fin"
                                    slotProps={{
                                        textField: { size: 'small', helperText: errorMessageEndDate },
                                        actionBar: {
                                            actions: ['clear']
                                        }
                                    }}
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
                <div className="filter__line filter__line-custom_prices_5 mt-1">
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
                            renderInput={(params) => <TextField key={params.id} value={params.id} {...params} label={`${t('formFilter.tariff')}`} />}
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
                            renderInput={(params) => <TextField key={params.id} value={params.id} {...params} label={`${t('formFilter.ticket')}`} />}
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
                    <FormControl size="small" className="form__control form__matselect" id="rmt-StudentType">
                        <InputLabel id="rmt-titleTypeLabel-content">{t('formFilter.typeStudent')}</InputLabel>
                        <Select
                            labelId="rmt-titleTypeLabel-content"
                            id="rmt-titleTypeSelect-content"
                            value={studentType}
                            label={t('labels.selectModule')}
                            onChange={(e: any) => {
                                setStudentType(e.target.value);
                            }}
                        >
                            {studentTypesOptions.map((item: any) => (
                                <MenuItem value={item.value} key={item.value}>
                                    {item.label}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                    <FormControl size="small" className="form__control formCourse__matAutocomplete" id="rmt-Status">
                        <Autocomplete
                            clearText={'Limpiar'}
                            openText=""
                            value={status}
                            options={statusData || []}
                            onChange={(e: any, newValue: any | null) => setStatus(newValue)}
                            disablePortal
                            noOptionsText={t('labels.noOptionsText')}
                            isOptionEqualToValue={(option, value) => option.id === value.id}
                            getOptionLabel={(option) => option?.description}
                            renderInput={(params) => <TextField key={params.id} value={params.id} {...params} label={`${t('formFilter.status')}`} />}
                            renderOption={(props, option: any) => (
                                <li {...props} value={option?.id} key={option?.id}>
                                    {option?.description}
                                </li>
                            )}
                        />
                    </FormControl>
                </div>
            </div>
            <div className="filter__buttons">
                <Button variant="contained" type="button" startIcon={<Search />} onClick={handleSubmit} disabled={!valid}>
                    {t('buttons.search')}
                </Button>
                <Button type="button" className="button-clean-filter" onClick={handleClean}>
                    LIMPIAR FILTROS
                </Button>
            </div>
        </form>
    );
};

export default FilterLogDisplay;
