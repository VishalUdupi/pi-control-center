Pi Control Centre

A self-hosted system monitoring and control centre for a Raspberry Pi, built with Java, Spring Boot, Redis, PostgreSQL, Docker, and Glances.

The project is designed as a lightweight backend for collecting system metrics from a Raspberry Pi, exposing them through REST APIs, and providing a foundation for storing both current and historical metrics.

Overview

Pi Control Centre acts as a central backend for monitoring the Raspberry Pi.

Instead of querying the operating system directly from the Spring Boot application, the project uses Glances as the system monitoring layer.

                    Raspberry Pi
                         │
        ┌────────────────┼────────────────┐
        │                │                │
     Glances          Redis           PostgreSQL
        │                │                │
        │          Current Metrics    Historical
        │             / Cache          Metrics
        │                │                │
        └────────── Spring Boot ──────────┘
                         │
                    REST API
                         │
                    Monitoring UI

The Spring Boot application acts as the application layer between Glances, Redis, PostgreSQL, and eventually a frontend.

Goals

The main goals of the project are:

Monitor Raspberry Pi system resources.

Collect metrics from Glances through its REST API.

Cache/current-state metrics in Redis.

Persist historical metrics in PostgreSQL.

Expose monitoring data through REST APIs.

Keep the architecture modular and extensible.

Run the infrastructure locally on the Raspberry Pi.

Avoid dependency on external monitoring SaaS platforms.

Technology Stack

Component

Technology

Language

Java

Framework

Spring Boot

Runtime

Java 21

System Monitoring

Glances

Cache / Current Metrics

Redis 8

Historical Storage

PostgreSQL

Containerization

Docker

Container Orchestration

Docker Compose

API Communication

Spring RestClient

Build Tool

Maven

Architecture

REST / layered backend

Hardware / Environment

The project currently runs on a:

Raspberry Pi 4B

ARM64 / aarch64

Debian GNU/Linux 13 (Trixie)

Java:

OpenJDK 21

Docker:

Docker Engine 29.8.1

Architecture

The application is divided into several logical layers.

                    ┌──────────────┐
                    │   Glances    │
                    │ System Data  │
                    └──────┬───────┘
                           │
                           │ REST API
                           ▼
                 ┌────────────────────┐
                 │   GlancesClient     │
                 │ Spring RestClient   │
                 └─────────┬──────────┘
                           │
                           ▼
                 ┌────────────────────┐
                 │ SystemMetricsService│
                 └─────────┬──────────┘
                           │
                    ┌──────┴──────┐
                    │             │
                    ▼             ▼
                 Redis        PostgreSQL
                Current       Historical
                Metrics        Metrics
                    │
                    ▼
             RedisMetricsStore
                    │
                    ▼
                 REST API

Glances

Glances is used as the system monitoring engine.

It provides information about the Raspberry Pi without requiring the Spring Boot application to directly interact with Linux system files or hardware sensors.

The application communicates with Glances through its API.

