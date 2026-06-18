#!/usr/bin/env bash
set -euo pipefail

PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
APP_DIR="$PROJECT_ROOT/demo"
PORT=8080

stop_processes() {
  local pids
  pids="$(ss -ltnp "( sport = :${PORT} )" 2>/dev/null | awk -F'pid=' 'NR>1 {print $2}' | awk -F',' '{print $1}' | sort -u || true)"

  if [[ -n "${pids}" ]]; then
    echo "Stopping processes on port ${PORT}: ${pids}"
    kill ${pids} || true
    sleep 1
  fi

  pkill -f 'mvn -f demo/pom.xml .*spring-boot:run' || true
  pkill -f 'com.example.Main' || true
}

start_app() {
  echo "Starting application from ${APP_DIR}"
  cd "${PROJECT_ROOT}"
  mvn -f demo/pom.xml clean spring-boot:run
}

stop_processes
start_app
