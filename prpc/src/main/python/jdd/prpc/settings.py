# -*- coding: utf-8 -*-

"""
common config
"""
# zk_path_setting
DEFAULT_ZK_NAMESPACE_SERVERS = "servers"
DEFAULT_ZK_NAMESPACE_ROOT = "jrpc"
DEFAULT_ZK_NAMESPACE_STATISTICS = "statistics"
DEFAULT_ZK_NAMESPACE_CLIENTS = "clients"
ZK_AUTH_USER = "admin"
ZK_AUTH_PASSWORD = "admin123"

# zk connect settings
DEFAULT_ZK_CONNECTION_TIMEOUT = 15  # unit s
DEFAULT_ZK_RETRY_MAX_DELAY = 60

# monitor statistics settings
SERVICE_MONITOR = "False"
DEFAULT_SEND_INFO_INTERVAL = 60  # unit s
DEFAULT_MAX_DETAILINFO_NODE_NUM = 600

# accelerate settings
USE_C_MODULE_SERIALIZE = "True"

"""
server config
"""
# server settings
DEFAULT_PROCESS_NUM = 10
DEFAULT_COROUTINES_NUM = 100


"""
    client config
"""
# service info

DEFAULT_REQUEST_RETRY = 3
DEFAULT_REQUEST_TIMEOUT = 3  # unit s
DEFAULT_MARK_DEAD_INTERVAL = 10  # unit s
DEFAULT_HEARTBEAT_RETRY = 3
DEFAULT_HEARTBEAT_TIMEOUT = 3  # unit s
DEFAULT_HEARTBEAT_INTERVAL = 10  # unit s
DEFAULT_USE_ZK = "True"
LOAD_BALANCE_PATH = "jdd.prpc.loadbalancing_strategy.round_robin_strategy.RoundRobinStrategy"

# connection pool settings
DEFAULT_POOL_SIZE = 100
DEFAULT_POOL_TIMEOUT = 3  # unit s
DEFAULT_POOL_MAX_WAIT = 2  # unit s
