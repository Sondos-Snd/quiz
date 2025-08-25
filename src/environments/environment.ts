export const environment = {
  production: false,
  apiBaseUrl: 'https://quiz-1-pwdb.onrender.com/api',
  keycloak: {
    url: 'https://keycloak-latest-q3o3.onrender.com/',   // when running KC locally; for prod see prod file
    realm: 'quiz',
    clientId: 'quiz-spa'
  }
};
