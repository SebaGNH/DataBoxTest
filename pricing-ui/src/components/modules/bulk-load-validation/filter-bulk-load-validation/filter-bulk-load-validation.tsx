import React, { useState, useEffect } from 'react';
import { Box, Button, FormControl, IconButton, InputAdornment, TextField, Typography } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';
import { useTranslation } from 'react-i18next';
import TableValidationSearch from './table-validation-search';
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { AdapterMoment } from '@mui/x-date-pickers/AdapterMoment';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import moment, { Moment } from 'moment';
import { Search } from '@mui/icons-material';

const FilterBulkLoadValidation = ({ isLoading, handleFilters, fileData, page, pageSize, totalElements, handlePage, csvIdParam }: any) => {
    const { t } = useTranslation();
    const [idLote, setIdLote] = useState<any>('');
    const [nombreArchivo, setNombreArchivo] = useState<any>('');
    const [startDate, setStartDate] = useState<any>(null);
    const [endDate, setEndDate] = useState<any>(null);
    const [errorStartDate, setErrorStartDate] = useState<any>(null);
    const [errorEndDate, setErrorEndDate] = useState<any>(null);
    const [errorMessageStartDate, setErrorMessageStartDate] = useState('');
    const [errorMessageEndDate, setErrorMessageEndDate] = useState('');
    const [valid, setValid] = useState<boolean>(true);

    const handleSubmit = (e: any) => {
        e.preventDefault();
        handleFilters({
            idLote,
            nombreArchivo,
            fechaMin: startDate && new Date(startDate.format('YYYY-MM-DD')).toISOString(),
            fechaMax: endDate && new Date(endDate.format('YYYY-MM-DD')).toISOString()
        });
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

    useEffect(() => {
        if (csvIdParam) setIdLote(csvIdParam);
    }, []);

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

    const handleClean = () => {
        setNombreArchivo('');
        setIdLote('');
        setStartDate(null);
        setEndDate(null);
        handleFilters({
            idLote: '',
            nombreArchivo: null,
            fechaMin: null,
            fechaMax: null
        });
    };

    return (
        <>
            <Box className="content_mass_validation_load">
                <Typography className="module_title" data-testid="module-title" gutterBottom>
                    {t('titles.mlValidation')}
                </Typography>
                <Typography className="module_subtitle1">{t('subtitles.subtitleValidation')}</Typography>
            </Box>
            <form className="filter__prices" onSubmit={handleSubmit}>
                <div className="filter__controls">
                    <div className="filter__line filter_bulk-load__line_4">
                        <FormControl size="small" className="form__control">
                            <TextField
                                className="filter__textfield"
                                size="small"
                                label={t('formFilter.id')}
                                variant="outlined"
                                type="text"
                                value={idLote}
                                inputProps={{ tabIndex: 0, pattern: '[0-9]*' }}
                                onChange={(e) => {
                                    e.target.validity.valid ? setIdLote(e.target.value) : setIdLote('');
                                }}
                            />
                        </FormControl>
                        <FormControl size="small" className="form__control">
                            <TextField
                                className="filter__textfield"
                                size="small"
                                label={'Archivo'}
                                variant="outlined"
                                type="text"
                                value={nombreArchivo}
                                onChange={(e) => {
                                    e.target.validity.valid ? setNombreArchivo(e.target.value) : setNombreArchivo('');
                                }}
                                InputProps={{
                                    endAdornment: (
                                        <InputAdornment position="end">
                                            <IconButton>
                                                <SearchIcon />
                                            </IconButton>
                                        </InputAdornment>
                                    )
                                }}
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
            <Box>
                <TableValidationSearch
                    isLoading={isLoading}
                    fileData={fileData}
                    page={page}
                    handlePage={handlePage}
                    pageSize={pageSize}
                    totalElements={totalElements}
                />
            </Box>
        </>
    );
};

export default FilterBulkLoadValidation;
