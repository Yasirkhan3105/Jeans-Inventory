const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api";

export async function apiRequest(path, credentials, options = {}) {
  const headers = {
    Accept: "application/json",
    ...options.headers,
  };

  if (credentials?.username && credentials?.password) {
    headers.Authorization = `Basic ${btoa(`${credentials.username}:${credentials.password}`)}`;
  }

  if (options.body) {
    headers["Content-Type"] = "application/json";
  }

  const response = await fetch(`${API_BASE_URL}${path}`, {
    ...options,
    headers,
  });

  if (!response.ok) {
    const errorBody = await response.json().catch(() => null);
    throw new Error(errorBody?.message || "Request failed. Check the Admin username and password.");
  }

  if (response.status === 204) {
    return null;
  }

  return response.json();
}

export function postJson(path, credentials, body) {
  return apiRequest(path, credentials, {
    method: "POST",
    body: JSON.stringify(body),
  });
}
