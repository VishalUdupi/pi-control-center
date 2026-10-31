```tsx
import { useCallback, useEffect, useState } from 'react';
import './App.css';
import { currentApi, historyApi } from './api/metricsApi';
import type {
  CpuStats,
  FileSystemStats,
  LoadStats,
  MemoryStats,
  NetworkStats,
  SensorStats,
} from './types/current';
import type {
  CpuHistory,
  LoadHistory,
  MemoryHistory,
  NetworkHistory,
  SensorHistory,
} from './types/history';

type CurrentState = {
  cpu: CpuStats | null;
  memory: MemoryStats | null;
  load: LoadStats | null;
  network: NetworkStats | null;
  filesystem: FileSystemStats | null;
  sensor: SensorStats | null;
  uptime: string | null;
};

type HistoryState = {
  cpu: CpuHistory[];
  memory: MemoryHistory[];
  load: LoadHistory[];
  network: NetworkHistory[];
  sensor: SensorHistory[];
};

const emptyCurrent: CurrentState = {
  cpu: null,
  memory: null,
  load: null,
  network: null,
  filesystem: null,
  sensor: null,
  uptime: null,
};

const emptyHistory: HistoryState = {
  cpu: [],
  memory: [],
  load: [],
  network: [],
  sensor: [],
};

function formatBytes(bytes?: number) {
  if (bytes == null || !Number.isFinite(bytes)) return '—';

  const units = ['B', 'KB', 'MB', 'GB', 'TB'];
  let value = bytes;
  let unit = 0;

  while (value >= 1024 && unit < units.length - 1) {
    value /= 1024;
    unit += 1;
  }

  return `${value.toFixed(unit === 0 ? 0 : 1)} ${units[unit]}`;
}

function formatRate(value?: number) {
  if (value == null || !Number.isFinite(value)) return '—';
  return `${formatBytes(value)}/s`;
}

function formatTime(value: string) {
  const date = new Date(value);

  return Number.isNaN(date.getTime())
      ? value
      : date.toLocaleTimeString([], {
        hour: '2-digit',
        minute: '2-digit',
      });
}

function MetricCard({
                      label,
                      value,
                      unit,
                      detail,
                      icon,
                      tone,
                    }: {
  label: string;
  value: string | number;
  unit?: string;
  detail: string;
  icon: string;
  tone: string;
}) {
  return (
      <article className="metric-card">
        <div className="metric-card-top">
          <span className="metric-label">{label}</span>
          <span className={`metric-icon ${tone}`}>{icon}</span>
        </div>

        <div className="metric-value">
          {value}
          <span>{unit}</span>
        </div>

        <div className="metric-detail">{detail}</div>
      </article>
  );
}

function MiniChart({
                     title,
                     subtitle,
                     points,
                     series,
                   }: {
  title: string;
  subtitle: string;
  points: Array<{ recordedAt: string; [key: string]: string | number }>;
  series: Array<{ key: string; label: string; color: string }>;
}) {
  const width = 620;
  const height = 190;
  const pad = 12;

  const values = points.flatMap((point) =>
      series
          .map((item) => Number(point[item.key]))
          .filter(Number.isFinite),
  );

  const max = Math.max(100, ...values, 1);

  const paths = series.map((item) => {
    const coords = points
        .map((point, index) => {
          const x =
              points.length < 2
                  ? width / 2
                  : pad + (index / (points.length - 1)) * (width - pad * 2);

          const y =
              height -
              pad -
              ((Math.max(0, Number(point[item.key]) || 0) / max) *
                  (height - pad * 2));

          return `${index === 0 ? 'M' : 'L'} ${x} ${y}`;
        })
        .join(' ');

    return { ...item, coords };
  });

  return (
      <article className="panel history-chart">
        <div className="panel-heading">
          <div>
            <h2>{title}</h2>
            <p>{subtitle}</p>
          </div>

          <span className="placeholder-badge">
          {points.length} records
        </span>
        </div>

        {points.length === 0 ? (
            <div className="history-empty">
              No historical records returned yet.
            </div>
        ) : (
            <div className="history-plot">
              <svg
                  viewBox={`0 0 ${width} ${height}`}
                  role="img"
                  aria-label={`${title} history chart`}
              >
                {[0, 1, 2, 3].map((line) => (
                    <line
                        key={line}
                        x1="0"
                        x2={width}
                        y1={pad + line * ((height - pad * 2) / 3)}
                        y2={pad + line * ((height - pad * 2) / 3)}
                        className="chart-rule"
                    />
                ))}

                {paths.map((path) => (
                    <path
                        key={path.key}
                        d={path.coords}
                        fill="none"
                        stroke={path.color}
                        strokeWidth="2.5"
                        strokeLinecap="round"
                        strokeLinejoin="round"
                    />
                ))}
              </svg>

              <div className="chart-axis">
                <span>{formatTime(points[0].recordedAt)}</span>
                <span>
              {formatTime(points[points.length - 1].recordedAt)}
            </span>
              </div>
            </div>
        )}

        <div className="chart-legend">
          {series.map((item) => (
              <span key={item.key}>
            <i
                className="legend-dot"
                style={{ background: item.color }}
            />
                {item.label}
          </span>
          ))}
        </div>
      </article>
  );
}

function App() {
  const [current, setCurrent] = useState<CurrentState>(emptyCurrent);
  const [history, setHistory] = useState<HistoryState>(emptyHistory);

  const [currentError, setCurrentError] = useState<string | null>(null);
  const [historyError, setHistoryError] = useState<string | null>(null);

  const [lastUpdated, setLastUpdated] = useState<Date | null>(null);
  const [connected, setConnected] = useState(false);

  const refreshCurrent = useCallback(async () => {
    const results = await Promise.allSettled([
      currentApi.cpu(),
      currentApi.memory(),
      currentApi.load(),
      currentApi.network(),
      currentApi.filesystem(),
      currentApi.sensor(),
      currentApi.uptime(),
    ]);

    const [
      cpu,
      memory,
      load,
      network,
      filesystem,
      sensor,
      uptime,
    ] = results;

    const failures = results.filter(
        (result) => result.status === 'rejected',
    ).length;

    setCurrent((previous) => ({
      cpu: cpu.status === 'fulfilled' ? cpu.value : previous.cpu,
      memory:
          memory.status === 'fulfilled' ? memory.value : previous.memory,
      load: load.status === 'fulfilled' ? load.value : previous.load,
      network:
          network.status === 'fulfilled' ? network.value : previous.network,
      filesystem:
          filesystem.status === 'fulfilled'
              ? filesystem.value
              : previous.filesystem,
      sensor:
          sensor.status === 'fulfilled' ? sensor.value : previous.sensor,
      uptime:
          uptime.status === 'fulfilled' ? uptime.value : previous.uptime,
    }));

    setConnected(results.some((result) => result.status === 'fulfilled'));

    setCurrentError(
        failures === results.length
            ? 'Could not reach the Spring Boot API. Check that it is running on port 8080 and CORS is configured.'
            : failures > 0
                ? `${failures} current metric request(s) failed. Check Redis data and backend logs.`
                : null,
    );

    setLastUpdated(new Date());
  }, []);

  const refreshHistory = useCallback(async () => {
    const results = await Promise.allSettled([
      historyApi.cpu(),
      historyApi.memory(),
      historyApi.load(),
      historyApi.network(),
      historyApi.sensor(),
    ]);

    const [cpu, memory, load, network, sensor] = results;

    const failures = results.filter(
        (result) => result.status === 'rejected',
    ).length;

    setHistory((previous) => ({
      cpu: cpu.status === 'fulfilled' ? cpu.value : previous.cpu,
      memory:
          memory.status === 'fulfilled' ? memory.value : previous.memory,
      load: load.status === 'fulfilled' ? load.value : previous.load,
      network:
          network.status === 'fulfilled' ? network.value : previous.network,
      sensor:
          sensor.status === 'fulfilled' ? sensor.value : previous.sensor,
    }));

    setHistoryError(
        failures
            ? `${failures} historical request(s) failed. Check the historical controller and PostgreSQL.`
            : null,
    );
  }, []);

  useEffect(() => {
    void refreshCurrent();
    void refreshHistory();

    const timer = window.setInterval(
        () => void refreshCurrent(),
        5000,
    );

    return () => window.clearInterval(timer);
  }, [refreshCurrent, refreshHistory]);

  const services = [
    {
      name: 'Spring Boot API',
      description: 'REST endpoints',
      status: connected ? 'Connected' : 'Not connected',
      online: connected,
    },
    {
      name: 'Redis',
      description: 'Current metrics store',
      status:
          current.cpu || current.memory ? 'Data available' : 'Not verified',
      online: Boolean(current.cpu || current.memory),
    },
    {
      name: 'PostgreSQL',
      description: 'Historical metrics store',
      status:
          history.cpu.length || history.memory.length
              ? 'Data available'
              : 'Not verified',
      online: history.cpu.length + history.memory.length > 0,
    },
  ];

  const latestCpu = history.cpu.slice(-60);
  const latestMemory = history.memory.slice(-60);

  const cpuPoints = latestCpu.map((item) => ({
    recordedAt: item.recordedAt,
    total: item.total,
    user: item.user,
  }));

  const memoryPoints = latestMemory.map((item) => ({
    recordedAt: item.recordedAt,
    percent: item.percent,
  }));

  return (
      <div className="app-shell">
        <aside className="sidebar">
          <a
              className="brand"
              href="#overview"
              aria-label="Pi Control Center home"
          >
            <span className="brand-mark">π</span>
            <span>
            Pi Control<span className="brand-muted"> Center</span>
          </span>
          </a>

          <div className="nav-label">WORKSPACE</div>

          <nav className="navigation" aria-label="Main navigation">
            <a className="nav-item active" href="#overview">
              <span>▦</span> Overview
            </a>
            <a className="nav-item" href="#performance">
              <span>⌁</span> Performance
            </a>
            <a className="nav-item" href="#network">
              <span>⌘</span> Network
            </a>
            <a className="nav-item" href="#storage">
              <span>▤</span> Storage
            </a>
          </nav>

          <div className="nav-label infrastructure-label">
            INFRASTRUCTURE
          </div>

          <nav
              className="navigation"
              aria-label="Infrastructure navigation"
          >
            <a className="nav-item" href="#services">
              <span>◈</span> Services
            </a>
            <a className="nav-item" href="#history">
              <span>◷</span> History
            </a>
          </nav>

          <div className="sidebar-bottom">
            <div className="pi-avatar">π</div>
            <div className="sidebar-device">
              <strong>Raspberry Pi</strong>
              <span>Control node</span>
            </div>
            <span className="more-icon">···</span>
          </div>
        </aside>

        <main className="main-content" id="overview">
          <header className="topbar">
            <div className="breadcrumb">
              Workspace <span>/</span> <strong>Overview</strong>
            </div>

            <div className="topbar-right">
            <span className="environment">
              <span
                  className={`environment-dot ${connected ? 'online' : ''}`}
              />
              {connected ? 'API connected' : 'Local environment'}
            </span>

              <button
                  className="icon-button"
                  type="button"
                  aria-label="Refresh metrics"
                  onClick={() => {
                    void refreshCurrent();
                    void refreshHistory();
                  }}
              >
                ↻
              </button>

              <div className="user-avatar">V</div>
            </div>
          </header>

          <div className="page-content">
            <section className="page-heading">
              <div>
                <div className="eyebrow">SYSTEM MONITORING</div>
                <h1>Overview</h1>
                <p className="page-description">
                  Monitor your Raspberry Pi's health and performance.
                </p>
              </div>

              <div className="updated-label">
              <span
                  className={`status-dot ${connected ? 'online' : ''}`}
              />
                {lastUpdated
                    ? `Updated ${lastUpdated.toLocaleTimeString()}`
                    : 'Connecting to backend'}
              </div>
            </section>

            {currentError && (
                <div className="api-alert" role="status">
                  {currentError}
                </div>
            )}

            <section
                className="metrics-grid"
                aria-label="Current system metrics"
            >
              <MetricCard
                  label="CPU Usage"
                  value={current.cpu ? current.cpu.total.toFixed(1) : '—'}
                  unit="%"
                  detail={
                    current.cpu
                        ? `${current.cpu.cores} cores · user ${current.cpu.user.toFixed(1)}% · I/O wait ${current.cpu.iowait.toFixed(1)}%`
                        : 'Waiting for Redis data'
                  }
                  icon="◫"
                  tone="blue"
              />

              <MetricCard
                  label="Memory Usage"
                  value={
                    current.memory
                        ? current.memory.percent.toFixed(1)
                        : '—'
                  }
                  unit="%"
                  detail={
                    current.memory
                        ? `${formatBytes(current.memory.used)} used of ${formatBytes(current.memory.total)}`
                        : 'Waiting for Redis data'
                  }
                  icon="▤"
                  tone="purple"
              />

              <MetricCard
                  label="CPU Temperature"
                  value={current.sensor?.value ?? '—'}
                  unit={current.sensor?.unit ?? ''}
                  detail={
                      current.sensor?.label ?? 'Waiting for sensor data'
                  }
                  icon="♨"
                  tone="orange"
              />

              <MetricCard
                  label="System Uptime"
                  value={current.uptime ?? '—'}
                  detail="From Redis current state"
                  icon="◷"
                  tone="green"
              />
            </section>

            <section className="content-grid" id="performance">
              <article className="panel performance-panel">
                <div className="panel-heading">
                  <div>
                    <h2>Current performance</h2>
                    <p>Latest values from Redis</p>
                  </div>
                  <span className="placeholder-badge">5 sec polling</span>
                </div>

                <div className="live-metrics">
                  <div>
                    <span>Load average</span>
                    <strong>
                      {current.load
                          ? `${current.load.min1.toFixed(2)} / ${current.load.min5.toFixed(2)} / ${current.load.min15.toFixed(2)}`
                          : '—'}
                    </strong>
                    <small>1, 5 and 15 minutes</small>
                  </div>

                  <div id="network">
                    <span>Network interface</span>
                    <strong>{current.network?.name ?? '—'}</strong>
                    <small>
                      Receive {formatRate(current.network?.recvRatePerSec)} ·
                      Send {formatRate(current.network?.sentRatePerSec)}
                    </small>
                  </div>

                  <div>
                    <span>Filesystem</span>
                    <strong>
                      {current.filesystem?.deviceName ?? '—'}
                    </strong>
                    <small>
                      {current.filesystem
                          ? `${current.filesystem.percent.toFixed(1)}% used · ${formatBytes(current.filesystem.free)} free`
                          : 'Waiting for Redis data'}
                    </small>
                  </div>
                </div>
              </article>

              <article className="panel services-panel" id="services">
                <div className="panel-heading">
                  <div>
                    <h2>Service health</h2>
                    <p>Observed through successful API responses</p>
                  </div>

                  <span className="service-count">
                  {services.filter((service) => service.online).length}/3
                </span>
                </div>

                <div className="service-list">
                  {services.map((service) => (
                      <div className="service-row" key={service.name}>
                        <div className="service-symbol">◇</div>

                        <div className="service-info">
                          <strong>{service.name}</strong>
                          <span>{service.description}</span>
                        </div>

                        <span
                            className={`service-status ${service.online ? 'online-text' : ''}`}
                        >
                      {service.status}
                    </span>
                      </div>
                  ))}
                </div>

                <div className="panel-footer">
                  Redis and PostgreSQL status is inferred from metric
                  responses, not a direct health check.
                </div>
              </article>
            </section>

            <section className="history-section" id="history">
              <div className="section-heading">
                <div>
                  <h2>Historical performance</h2>
                  <p>Records returned by PostgreSQL</p>
                </div>

                <button
                    className="select-button"
                    type="button"
                    onClick={() => void refreshHistory()}
                >
                  Refresh history ↻
                </button>
              </div>

              {historyError && (
                  <div className="api-alert">{historyError}</div>
              )}

              <div className="history-grid">
                <MiniChart
                    title="CPU history"
                    subtitle="Total and user utilization (%)"
                    points={cpuPoints}
                    series={[
                      {
                        key: 'total',
                        label: 'Total',
                        color: '#9b8cff',
                      },
                      {
                        key: 'user',
                        label: 'User',
                        color: '#52b8d1',
                      },
                    ]}
                />

                <MiniChart
                    title="Memory history"
                    subtitle="Memory utilization (%)"
                    points={memoryPoints}
                    series={[
                      {
                        key: 'percent',
                        label: 'Used',
                        color: '#52b8d1',
                      },
                    ]}
                />

                <MiniChart
                    title="Load history"
                    subtitle="Load averages"
                    points={history.load.slice(-60).map((item) => ({
                      recordedAt: item.recordedAt,
                      min1: item.min1,
                      min5: item.min5,
                      min15: item.min15,
                    }))}
                    series={[
                      {
                        key: 'min1',
                        label: '1 min',
                        color: '#9b8cff',
                      },
                      {
                        key: 'min5',
                        label: '5 min',
                        color: '#52b8d1',
                      },
                      {
                        key: 'min15',
                        label: '15 min',
                        color: '#e5a45c',
                      },
                    ]}
                />

                <MiniChart
                    title="Network history"
                    subtitle="Receive and send rates (bytes/sec)"
                    points={history.network.slice(-60).map((item) => ({
                      recordedAt: item.recordedAt,
                      recvRatePerSec: item.recvRatePerSec,
                      sentRatePerSec: item.sentRatePerSec,
                    }))}
                    series={[
                      {
                        key: 'recvRatePerSec',
                        label: 'Receive',
                        color: '#52b8d1',
                      },
                      {
                        key: 'sentRatePerSec',
                        label: 'Send',
                        color: '#9b8cff',
                      },
                    ]}
                />

                <MiniChart
                    title="Sensor history"
                    subtitle="Recorded sensor values"
                    points={history.sensor.slice(-60).map((item) => ({
                      recordedAt: item.recordedAt,
                      value: item.value,
                    }))}
                    series={[
                      {
                        key: 'value',
                        label: history.sensor[0]?.unit || 'Sensor',
                        color: '#e5a45c',
                      },
                    ]}
                />
              </div>
            </section>

            <section className="panel system-panel" id="storage">
              <div className="panel-heading">
                <div>
                  <h2>System information</h2>
                  <p>
                    Filesystem and network details available from current
                    metrics
                  </p>
                </div>

                <span className="placeholder-badge">
                {current.filesystem ? 'Data available' : 'Awaiting data'}
              </span>
              </div>

              <div className="system-info-grid">
                <div>
                  <span>Filesystem device</span>
                  <strong>
                    {current.filesystem?.deviceName ?? '—'}
                  </strong>
                </div>

                <div>
                  <span>Mount point</span>
                  <strong>{current.filesystem?.mntPoint ?? '—'}</strong>
                </div>

                <div>
                  <span>Filesystem size</span>
                  <strong>{formatBytes(current.filesystem?.size)}</strong>
                </div>

                <div>
                  <span>Filesystem used</span>
                  <strong>
                    {current.filesystem
                        ? `${current.filesystem.percent.toFixed(1)}% · ${formatBytes(current.filesystem.used)}`
                        : '—'}
                  </strong>
                </div>
              </div>
            </section>

            <footer className="page-footer">
            <span>
              Pi Control Center <span className="footer-separator">·</span>{' '}
              System dashboard
            </span>

              <span>
              REST API <span className="footer-separator">·</span> No
              WebSockets
            </span>
            </footer>
          </div>
        </main>
      </div>
  );
}

export default App;