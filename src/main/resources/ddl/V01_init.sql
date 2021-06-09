create table if not exists product (
    id int auto_increment primary key comment 'id',
    jan varchar(63) not null comment 'janコード',
    name varchar(255) not null comment '商品名',
    fulltext (jan, name),
    constraint unique index (jan)
)
character set 'utf8mb4';

create table if not exists wholesale_price (
    id int auto_increment primary key comment 'id',
    jan varchar(63) not null comment 'janコード',
    wholesale_price decimal(9,2) not null comment '仕切',
    conditions varchar(255) comment '条件',
    net_wholesale_price decimal(9,2) not null comment 'NET',
    default_price_flag int default 0 comment 'デフォルト仕入れ値フラグ。1: デフォルト、0: デフォルトでない'
)
character set 'utf8mb4';

create index i_wholesale_price on wholesale_price (jan);

create table if not exists retailer (
    id int auto_increment primary key comment 'id',
    name varchar(127) not null comment '卸先名',
    constraint unique index(name)
)
character set 'utf8mb4';

create table if not exists manufacturer (
    id int auto_increment primary key comment 'id',
    name varchar(127) not null comment 'メーカー',
    constraint unique index(name)
)
character set 'utf8mb4';

create table if not exists retailer_wholesale_price (
    retailer_id int not null,
    wholesale_price_id int not null,
    primary key (retailer_id, wholesale_price_id)
)
character set 'utf8mb4';

create table if not exists wholesale_estimate (
    id int auto_increment primary key comment 'id',
    retailer_id int comment '卸先ID',
    manufacturer_id int not null comment 'メーカーID',
    title varchar(127) comment 'タイトル',
    estimate_date date not null comment '見積日'
)
character set 'utf8mb4';

create table if not exists wholesale_estimate_detail (
    id int auto_increment primary key comment 'id',
    wholesale_estimate_id int not null comment '卸向け見積ID',
    selling_start_date date comment '発売日',
    product_category varchar(31) comment '部門',
    product_jan varchar(63) not null comment 'JANコード',
    product_name varchar(255) not null comment '商品名',
    product_manufacturer varchar(127) comment 'メーカー',
    product_specification varchar(31) comment '規格',
    count varchar(31) not null comment '入数',
    wholesale_price decimal(9,2) not null comment '仕切',
    conditions varchar(255) comment '条件',
    net_wholesale_price decimal(9,2) not null comment 'NET',
    notes varchar(511) comment '備考'
)
character set 'utf8mb4';

create table if not exists retailer_estimate (
    id int auto_increment primary key comment 'id',
    retailer_id int comment '卸先id',
    address varchar(255) comment '宛名',
    issue_date date comment '見積日',
    due_date date comment '有効期限',
    total decimal(9,2) not null comment '合計(税込)',
    total_without_tax decimal(9,2) not null comment '合計(税抜)',
    total_tax decimal(9,2) not null comment '消費税等合',
    total_cost decimal(9,2) not null comment '原価合計',
    notes varchar(511) comment '備考'
)
character set 'utf8mb4';

create table if not exists retailer_estimate_detail (
    id int auto_increment primary key comment 'id',
    order_no int not null comment '明細番号',
    retailer_estimate_id int not null comment '小売向け見積ID',
    selling_start_date date comment '発売日',
    product_category varchar(31) comment '部門',
    product_jan varchar(63) not null comment 'JANコード',
    product_name varchar(255) not null comment '商品名',
    product_manufacturer varchar(127) comment 'メーカー',
    product_specification varchar(31) comment '規格',
    count decimal(9,2) not null comment '個数',
    unit_price decimal(9,2) not null comment '卸単価',
    tax_rate decimal(5,3) not null default 0.1 comment '税率',
    wholesale_price decimal(9,2) not null comment '仕入単価'
)
character set 'utf8mb4';