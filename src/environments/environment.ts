export const environment = {
  production: false,
  apiBaseUrl: 'http://localhost:8081/api',
  keycloak: {
    url: 'http://localhost:8080',   // when running KC locally; for prod see prod file
    realm: 'quiz',
    clientId: 'quiz-spa'
  }
};
