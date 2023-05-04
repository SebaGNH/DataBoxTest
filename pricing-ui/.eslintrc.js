module.exports = {
    env: {
        browser: true,
        es2021: true,
        jest: true
    },
    extends: ['plugin:react/recommended', 'standard', 'plugin:cypress/recommended'],
    parser: '@typescript-eslint/parser',
    parserOptions: {
        ecmaFeatures: {
            jsx: true
        },
        ecmaVersion: 'latest',
        sourceType: 'module'
    },
    plugins: ['react', '@typescript-eslint', 'cypress'],
    rules: {
        semi: [2, 'always'],
        indent: ['error', 4, { SwitchCase: 1 }],
        curly: ['off', 'multi'],
        'space-before-function-paren': 0,
        'multiline-ternary': 0,
        'react/display-name': [0]
    }
};
