import Keycloak, { KeycloakInstance } from 'keycloak-js';
import { environment } from '../../../../environments/environment.prod';

let keycloak: KeycloakInstance;

export function getKeycloak() {
  return keycloak;
}

export async function initKeycloak(): Promise<void> {
  keycloak = new Keycloak({
    url: environment.keycloak.url,
    realm: environment.keycloak.realm,
    clientId: environment.keycloak.clientId
  });

  await keycloak.init({
    onLoad: 'check-sso',     // or 'login-required' to force login
    pkceMethod: 'S256',
    checkLoginIframe: false
  });

  // Optionally refresh tokens automatically
  setInterval(() => {
    keycloak.updateToken(60).catch(() => keycloak.login());
  }, 30000);
}
