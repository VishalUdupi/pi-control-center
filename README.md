Pi Control Center

A lightweight, self-hosted monitoring dashboard for Raspberry Pi and homelab infrastructure.

  Pi Control Center is designed to provide a centralized view of system metrics and service health through a web dashboard, backed by a Java Spring Boot application. It prioritizes low resource     consumption, a clear separation of responsibilities, and straightforward deployment on resource-constrained hardware.

Project Goals

  Low resource usage: Designed to run on a Raspberry Pi without unnecessary overhead.
  
  Centralized monitoring: Bring system metrics and service health into one dashboard.
  
  Fast access to current metrics: Use Redis to hold the latest collected state.
  
  Historical data: Use PostgreSQL to store metrics for historical analysis.
  
  Simple communication: Use REST APIs for communication between the frontend and backend.
  
  No WebSockets: Keep the communication model straightforward and easier to maintain.
  
  Self-hosted infrastructure: Keep monitoring data and services under your control.

Technology Stack

  Component: 
    
    Technology:

      Backend

        Java 21, Spring Boot

      Frontend

        React, TypeScript, Vite

      Current-state storage

        Redis

      Historical storage

        PostgreSQL

      System metrics

        Glances API

      Build system

        Maven

      Containerization

        Docker

      CI/CD

        Jenkins

      Communication

        REST APIs

Architecture

  The application separates metric collection, storage, API access, and presentation.
  
                   Raspberry Pi / Homelab
                            |
                         Glances
                            |
                      GlancesClient
                            |
                   SystemMetricsService
                            |
                  +---------+---------+
                  |                   |
                Redis             PostgreSQL
            Current state       Historical data
                  |                   |
                  +---------+---------+
                            |
                      Spring Boot
                        REST API
                            |
                         React UI

Backend

  The Spring Boot backend is responsible for collecting system metrics, coordinating data storage, and exposing information through REST endpoints.
  
  Glances integration: Retrieves system information through the Glances API.
  
  Metric collection: Collects system metrics on scheduled intervals.
  
  Redis: Stores the latest metric values for quick access.
  
  PostgreSQL: Provides persistent storage for historical metrics.
  
  REST API: Supplies data to the frontend without requiring WebSockets.
  
  Data storage
  
  Redis and PostgreSQL serve different purposes:
  
  Redis is intended for frequently updated, current system state.
  
  PostgreSQL is intended for persistent historical data and future trend analysis.
  
  This separation avoids treating the historical database as the primary source for every current dashboard refresh.

Frontend

  The frontend uses React and TypeScript, with Vite as the development and build tool.
  
  The dashboard is being developed to present:
  
  CPU utilization and system load
  
  Memory usage
  
  CPU temperature and other available sensor data
  
  System uptime and information
  
  Service health
  
  Historical metric charts
  
  These are the dashboard's planned presentation areas; their availability depends on the corresponding backend endpoints and data collection being implemented.
