import React, { useMemo, useCallback } from 'react';
import { useDropzone } from 'react-dropzone';
import image from '../../../../assets/img/upload_file_black.png';

const baseStyle = {
    flex: 1,
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    padding: '20px',
    borderWidth: 2,
    borderRadius: 2,
    borderColor: '#eeeeee',
    borderStyle: 'dashed',
    backgroundColor: '#fafafa',
    color: '#bdbdbd',
    outline: 'none',
    transition: 'border .24s ease-in-out'
};

const focusedStyle = {
    borderColor: '#2196f3'
};

const acceptStyle = {
    borderColor: '#00e676'
};

const rejectStyle = {
    borderColor: '#ff1744'
};

function DropzoneFile({ setFile }: any) {
    const onDrop = useCallback((files: any) => {
        const file = files[0];
        setFile(file);
    }, []);

    const { getRootProps, getInputProps, isFocused, isDragAccept, isDragReject } = useDropzone({
        onDrop,
        accept: {
            'text/csv': ['.csv']
        }
    });

    const style: any = useMemo(
        () => ({
            ...baseStyle,
            ...(isFocused ? focusedStyle : {}),
            ...(isDragAccept ? acceptStyle : {}),
            ...(isDragReject ? rejectStyle : {})
        }),
        [isFocused, isDragAccept, isDragReject]
    );

    return (
        <>
            <div {...getRootProps({ style })}>
                <img src={image} />
                <input {...getInputProps()} />
                <h3>Solta el archivo o examina acá</h3>
                <p>Solo formato CSV hasta 30 mb</p>
            </div>
        </>
    );
}
export default DropzoneFile;
