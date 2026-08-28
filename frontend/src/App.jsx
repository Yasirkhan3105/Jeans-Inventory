import { useEffect, useMemo, useState } from "react";
import { apiRequest, postJson } from "./api";

const PAGES = [
  ["dashboard", "Dashboard"],
  ["styles", "Styles"],
  ["normal-stock", "Normal Stock"],
  ["assortment", "Shop Assortment"],
  ["samples", "Main Stock"],
  ["partners", "Fabricators & Washers"],
  ["movement", "Move Stock"],
  ["wastage", "Wastage & Weaving"],
  ["alters", "Alters"],
  ["pricing", "Pricing"],
  ["reports", "Reports"],
];

const SIZES = [30, 32, 34, 36];

const LOCATIONS = ["SHOP", "FINISHING"];

const HOLDER_TYPES = [
  "LOCATION",
  "FABRICATOR",
  "WASHER",
];

const WASTAGE_STAGES = [
  "WEAVING",
  "FABRICATOR",
  "WASHER",
  "FINISHING",
  "SHOP",
  "GODOWN",
  "OTHER",
];

const emptyHolder = () => ({
  holderType: "LOCATION",
  location: "SHOP",
  partnerId: "",
});

function App() {
  const [credentials, setCredentials] = useState(null);
  const [page, setPage] = useState("dashboard");

  const [styles, setStyles] = useState([]);
  const [partners, setPartners] = useState([]);
  const [summary, setSummary] = useState(null);

  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const refreshCoreData = async (activeCredentials = credentials) => {
    const [styleData, partnerData, dashboardData] = await Promise.all([
      apiRequest("/styles", activeCredentials),
      apiRequest("/partners", activeCredentials),
      apiRequest("/dashboard", activeCredentials),
    ]);

    setStyles(styleData);
    setPartners(partnerData);
    setSummary(dashboardData);
  };

  useEffect(() => {
    if (!credentials) return;

    refreshCoreData().catch((requestError) => {
      setError(requestError.message);
    });
  }, [credentials]);

  const runAction = async (action, successMessage) => {
    setMessage("");
    setError("");

    try {
      await action();
      await refreshCoreData();
      setMessage(successMessage);
    } catch (requestError) {
      setError(requestError.message);
    }
  };

  if (!credentials) {
    return <Login onLogin={setCredentials} />;
  }

  return (
      <main className="app-shell">
        <aside className="sidebar">
          <div className="brand">
            <span className="brand-mark">JI</span>

            <div>
              <strong>Jeans Inventory</strong>
              <small>Manufacturing control</small>
            </div>
          </div>

          <nav>
            {PAGES.map(([id, label]) => (
                <button
                    className={page === id ? "nav-link active" : "nav-link"}
                    key={id}
                    onClick={() => {
                      setPage(id);
                      setMessage("");
                      setError("");
                    }}
                >
                  {label}
                </button>
            ))}
          </nav>

          <button
              className="logout-button"
              onClick={() => setCredentials(null)}
          >
            Sign out
          </button>
        </aside>

        <section className="workspace">
          <header className="topbar">
            <div>
              <p className="eyebrow">ADMIN PANEL</p>
              <h1>{PAGES.find(([id]) => id === page)?.[1]}</h1>
            </div>

            <div className="admin-badge">
              Admin: {credentials.username}
            </div>
          </header>

          {message && (
              <div className="notice success">
                {message}
              </div>
          )}

          {error && (
              <div className="notice error">
                {error}
              </div>
          )}

          {page === "dashboard" && (
              <Dashboard summary={summary} />
          )}

          {page === "styles" && (
              <StylesPage
                  credentials={credentials}
                  styles={styles}
                  runAction={runAction}
              />
          )}

          {page === "normal-stock" && (
              <NormalStockPage
                  credentials={credentials}
                  styles={styles}
                  partners={partners}
                  runAction={runAction}
              />
          )}

          {page === "assortment" && (
              <AssortmentPage
                  credentials={credentials}
                  styles={styles}
                  runAction={runAction}
              />
          )}

          {page === "samples" && (
              <SamplesPage
                  credentials={credentials}
                  styles={styles}
                  runAction={runAction}
              />
          )}

          {page === "partners" && (
              <PartnersPage
                  credentials={credentials}
                  partners={partners}
                  runAction={runAction}
              />
          )}

          {page === "movement" && (
              <MovementPage
                  credentials={credentials}
                  styles={styles}
                  partners={partners}
                  runAction={runAction}
              />
          )}

          {page === "wastage" && (
              <WastagePage
                  credentials={credentials}
                  styles={styles}
                  partners={partners}
                  runAction={runAction}
              />
          )}

          {page === "alters" && (
              <AltersPage
                  credentials={credentials}
                  styles={styles}
                  partners={partners}
                  runAction={runAction}
              />
          )}

          {page === "pricing" && (
              <PricingPage
                  credentials={credentials}
                  styles={styles}
                  runAction={runAction}
              />
          )}

          {page === "reports" && (
              <ReportsPage
                  credentials={credentials}
              />
          )}
        </section>
      </main>
  );
}


/* =========================================================
   LOGIN
   ========================================================= */

function Login({ onLogin }) {
  const [username, setUsername] = useState("admin");
  const [password, setPassword] = useState("");

  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const submit = async (event) => {
    event.preventDefault();

    setLoading(true);
    setError("");

    try {
      const credentials = {
        username,
        password,
      };

      await apiRequest("/dashboard", credentials);

      onLogin(credentials);
    } catch (requestError) {
      setError(requestError.message);
    } finally {
      setLoading(false);
    }
  };

  return (
      <main className="login-page">
        <form
            className="login-card"
            onSubmit={submit}
        >
        <span className="brand-mark large">
          JI
        </span>

          <p className="eyebrow">
            JEANS MANUFACTURING
          </p>

          <h1>
            Inventory control
          </h1>

          <p className="muted">
            Sign in with the Admin account configured in the backend.
          </p>

          {error && (
              <div className="notice error">
                {error}
              </div>
          )}

          <label>
            Username

            <input
                value={username}
                onChange={(event) =>
                    setUsername(event.target.value)
                }
                required
            />
          </label>

          <label>
            Password

            <input
                type="password"
                value={password}
                onChange={(event) =>
                    setPassword(event.target.value)
                }
                required
            />
          </label>

          <button
              className="primary-button"
              disabled={loading}
          >
            {loading ? "Signing in…" : "Sign in"}
          </button>
        </form>
      </main>
  );
}


/* =========================================================
   DASHBOARD
   ========================================================= */

function Dashboard({ summary }) {
  if (!summary) {
    return (
        <div>
          <p className="page-intro">
            Loading dashboard...
          </p>
        </div>
    );
  }

  const cards = [
    ["Styles", summary.totalStyles],
    ["Shop", summary.shopStock],
    ["Finishing", summary.finishingStock],
    ["At Fabricators", summary.fabricatorStock],
    ["At Washers", summary.washerStock],
    ["Shop Extras", summary.shopAssortmentPieces],
    ["Main Stock", summary.samplePieces],
    ["Wastage", summary.wastagePieces],
    ["Alters", summary.alterPieces],
  ];

  const valueCards = [
    ["Normal Stock Value", summary.normalStockValue],
    ["Fabricator Stock Value", summary.fabricatorStockValue],
    ["Washer Stock Value", summary.washerStockValue],
    ["Total Inventory Value", summary.totalInventoryValue],
  ];

  return (
      <div>
        <p className="page-intro">
          Live inventory quantities and valuation across your current records.
        </p>

        <h2 className="section-heading">
          Inventory Pieces
        </h2>

        <div className="metric-grid">
          {cards.map(([label, value]) => (
              <article
                  className="metric-card"
                  key={label}
              >
                <span>{label}</span>

                <strong>
                  {Number(value || 0).toLocaleString()}
                </strong>
              </article>
          ))}
        </div>

        <h2 className="section-heading">
          Inventory Valuation
        </h2>

        <div className="metric-grid">
          {valueCards.map(([label, value]) => (
              <article
                  className="metric-card value-card"
                  key={label}
              >
                <span>{label}</span>

                <strong>
                  {formatCurrency(value)}
                </strong>
              </article>
          ))}
        </div>

        <article className="info-card">
          <h2>
            How stock flows
          </h2>

          <p>
            Use <strong>Normal Stock</strong> to add or remove opening
            balances. Use <strong>Move Stock</strong> for transfers.
            Movements update both source and destination balances together.
          </p>

          <p>
            Dashboard valuation uses the pricing information recorded
            against each style.
          </p>
        </article>
      </div>
  );
}


/* =========================================================
   STYLES
   ========================================================= */

function StylesPage({
                      credentials,
                      styles,
                      runAction,
                    }) {
  const [form, setForm] = useState({
    styleCode: "",
    colour: "",
    wash: "",
    fabric: "",
  });

  const submit = (event) => {
    event.preventDefault();

    runAction(
        async () => {
          await postJson(
              "/styles",
              credentials,
              form
          );

          setForm({
            styleCode: "",
            colour: "",
            wash: "",
            fabric: "",
          });
        },
        "Style added."
    );
  };

  return (
      <PageLayout
          form={
            <form
                onSubmit={submit}
                className="form-grid"
            >
              <TextInput
                  label="Style code"
                  value={form.styleCode}
                  onChange={(value) =>
                      setForm({
                        ...form,
                        styleCode: value,
                      })
                  }
                  required
              />

              <TextInput
                  label="Colour"
                  value={form.colour}
                  onChange={(value) =>
                      setForm({
                        ...form,
                        colour: value,
                      })
                  }
                  required
              />

              <TextInput
                  label="Wash"
                  value={form.wash}
                  onChange={(value) =>
                      setForm({
                        ...form,
                        wash: value,
                      })
                  }
              />

              <TextInput
                  label="Fabric"
                  value={form.fabric}
                  onChange={(value) =>
                      setForm({
                        ...form,
                        fabric: value,
                      })
                  }
              />

              <SubmitButton label="Add style" />
            </form>
          }

          table={
            <SimpleTable
                columns={[
                  "Code",
                  "Colour",
                  "Wash",
                  "Fabric",
                ]}
                rows={styles.map((style) => [
                  style.styleCode,
                  style.colour,
                  style.wash || "—",
                  style.fabric || "—",
                ])}
            />
          }
      />
  );
}


/* =========================================================
   NORMAL STOCK
   ========================================================= */

function NormalStockPage({
                           credentials,
                           styles,
                           partners,
                           runAction,
                         }) {
  const [form, setForm] = useState({
    styleId: "",
    holderType: "LOCATION",
    location: "SHOP",
    partnerId: "",
    operation: "increase",
    quantity: "",
  });

  const matchingPartners = partners.filter(
      (partner) =>
          partner.active &&
          partner.partnerType === form.holderType
  );

  const submit = (event) => {
    event.preventDefault();

    const body = {
      styleId: Number(form.styleId),
      holderType: form.holderType,
      location:
          form.holderType === "LOCATION"
              ? form.location
              : null,
      partnerId:
          form.holderType === "LOCATION"
              ? null
              : Number(form.partnerId),
      operation: form.operation,
      quantity: Number(form.quantity),
    };

    runAction(
        async () => {
          await postJson(
              "/stock/adjust",
              credentials,
              body
          );

          setForm({
            styleId: "",
            holderType: "LOCATION",
            location: "SHOP",
            partnerId: "",
            operation: "increase",
            quantity: "",
          });
        },
        form.operation === "increase"
            ? "Normal stock increased successfully."
            : "Normal stock decreased successfully."
    );
  };

  return (
      <PageLayout
          description="Adjust normal stock directly for Shop, Finishing, Fabricators, or Washers. Use this for opening balances or manual stock corrections."
          form={
            <form
                onSubmit={submit}
                className="form-grid movement-form"
            >
              <StyleSelect
                  styles={styles}
                  value={form.styleId}
                  onChange={(value) =>
                      setForm({
                        ...form,
                        styleId: value,
                      })
                  }
              />

              <SelectInput
                  label="Stock holder"
                  value={form.holderType}
                  onChange={(value) =>
                      setForm({
                        ...form,
                        holderType: value,
                        location: "SHOP",
                        partnerId: "",
                      })
                  }
                  options={HOLDER_TYPES}
              />

              {form.holderType === "LOCATION" ? (
                  <SelectInput
                      label="Location"
                      value={form.location}
                      onChange={(value) =>
                          setForm({
                            ...form,
                            location: value,
                          })
                      }
                      options={LOCATIONS}
                  />
              ) : (
                  <SelectInput
                      label={`${form.holderType.toLowerCase()} name`}
                      value={form.partnerId}
                      onChange={(value) =>
                          setForm({
                            ...form,
                            partnerId: value,
                          })
                      }
                      options={matchingPartners.map(
                          (partner) => ({
                            value: partner.id,
                            label: partner.name,
                          })
                      )}
                  />
              )}

              <SelectInput
                  label="Operation"
                  value={form.operation}
                  onChange={(value) =>
                      setForm({
                        ...form,
                        operation: value,
                      })
                  }
                  options={[
                    {
                      value: "increase",
                      label: "Increase stock",
                    },
                    {
                      value: "decrease",
                      label: "Decrease stock",
                    },
                  ]}
              />

              <NumberInput
                  label="Quantity"
                  value={form.quantity}
                  onChange={(value) =>
                      setForm({
                        ...form,
                        quantity: value,
                      })
                  }
              />

              <SubmitButton
                  label={
                    form.operation === "increase"
                        ? "Increase stock"
                        : "Decrease stock"
                  }
              />
            </form>
          }
      />
  );
}


/* =========================================================
   SHOP ASSORTMENT
   ========================================================= */

function AssortmentPage({
                          credentials,
                          styles,
                          runAction,
                        }) {
  const [form, setForm] = useState({
    styleId: "",
    size: "30",
    quantity: "",
  });

  const submit = (event) => {
    event.preventDefault();

    runAction(
        async () => {
          await postJson(
              "/shop-assortment",
              credentials,
              {
                style: {
                  id: Number(form.styleId),
                },
                size: Number(form.size),
                quantity: Number(form.quantity),
              }
          );

          setForm({
            styleId: "",
            size: "30",
            quantity: "",
          });
        },
        "Shop assortment updated."
    );
  };

  return (
      <PageLayout
          description="Extra shop pieces are tracked separately by jean size."
          form={
            <form
                onSubmit={submit}
                className="form-grid"
            >
              <StyleSelect
                  styles={styles}
                  value={form.styleId}
                  onChange={(value) =>
                      setForm({
                        ...form,
                        styleId: value,
                      })
                  }
              />

              <SelectInput
                  label="Jean size"
                  value={form.size}
                  onChange={(value) =>
                      setForm({
                        ...form,
                        size: value,
                      })
                  }
                  options={SIZES}
              />

              <NumberInput
                  label="Quantity"
                  value={form.quantity}
                  onChange={(value) =>
                      setForm({
                        ...form,
                        quantity: value,
                      })
                  }
              />

              <SubmitButton
                  label="Add extra pieces"
              />
            </form>
          }
      />
  );
}


/* =========================================================
   SAMPLES
   ========================================================= */

function SamplesPage({
                       credentials,
                       styles,
                       runAction,
                     }) {
  const [form, setForm] = useState({
    styleId: "",
    location: "SHOP",
    quantity: "",
  });

  const submit = (event) => {
    event.preventDefault();

    runAction(
        async () => {
          await postJson(
              "/samples",
              credentials,
              {
                style: {
                  id: Number(form.styleId),
                },
                location: form.location,
                quantity: Number(form.quantity),
              }
          );

          setForm({
            styleId: "",
            location: "SHOP",
            quantity: "",
          });
        },
        "Main stock updated."
    );
  };

  return (
      <PageLayout
          description="Main stock is separate from normal stock and has no size tracking."
          form={
            <form
                onSubmit={submit}
                className="form-grid"
            >
              <StyleSelect
                  styles={styles}
                  value={form.styleId}
                  onChange={(value) =>
                      setForm({
                        ...form,
                        styleId: value,
                      })
                  }
              />

              <SelectInput
                  label="Main stock location"
                  value={form.location}
                  onChange={(value) =>
                      setForm({
                        ...form,
                        location: value,
                      })
                  }
                  options={[
                    "SHOP",
                    "GODOWN",
                  ]}
              />

              <NumberInput
                  label="Quantity"
                  value={form.quantity}
                  onChange={(value) =>
                      setForm({
                        ...form,
                        quantity: value,
                      })
                  }
              />

              <SubmitButton
                  label="Add main stock"
              />
            </form>
          }
      />
  );
}


/* =========================================================
   PARTNERS
   ========================================================= */

function PartnersPage({
                        credentials,
                        partners,
                        runAction,
                      }) {
  const [form, setForm] = useState({
    partnerType: "FABRICATOR",
    name: "",
  });

  const submit = (event) => {
    event.preventDefault();

    runAction(
        async () => {
          await postJson(
              "/partners",
              credentials,
              form
          );

          setForm({
            partnerType: "FABRICATOR",
            name: "",
          });
        },
        "Manufacturing partner added."
    );
  };

  return (
      <PageLayout
          description="Every fabricator and washer has a separate named balance."
          form={
            <form
                onSubmit={submit}
                className="form-grid"
            >
              <SelectInput
                  label="Partner type"
                  value={form.partnerType}
                  onChange={(value) =>
                      setForm({
                        ...form,
                        partnerType: value,
                      })
                  }
                  options={[
                    "FABRICATOR",
                    "WASHER",
                  ]}
              />

              <TextInput
                  label="Name"
                  value={form.name}
                  onChange={(value) =>
                      setForm({
                        ...form,
                        name: value,
                      })
                  }
                  required
              />

              <SubmitButton
                  label="Add partner"
              />
            </form>
          }

          table={
            <SimpleTable
                columns={[
                  "Type",
                  "Name",
                  "Status",
                ]}
                rows={partners.map((partner) => [
                  partner.partnerType,
                  partner.name,
                  partner.active
                      ? "Active"
                      : "Inactive",
                ])}
            />
          }
      />
  );
}


/* =========================================================
   MOVEMENT
   ========================================================= */

function MovementPage({
                        credentials,
                        styles,
                        partners,
                        runAction,
                      }) {
  const [form, setForm] = useState({
    styleId: "",
    from: emptyHolder(),
    to: {
      holderType: "FABRICATOR",
      location: "SHOP",
      partnerId: "",
    },
    quantity: "",
    remarks: "",
  });

  const submit = (event) => {
    event.preventDefault();

    const body = {
      styleId: Number(form.styleId),

      ...holderPayload(
          "from",
          form.from
      ),

      ...holderPayload(
          "to",
          form.to
      ),

      quantity: Number(form.quantity),

      remarks: form.remarks,
    };

    runAction(
        async () => {
          await postJson(
              "/movements",
              credentials,
              body
          );

          setForm({
            styleId: "",
            from: emptyHolder(),
            to: {
              holderType: "FABRICATOR",
              location: "SHOP",
              partnerId: "",
            },
            quantity: "",
            remarks: "",
          });
        },
        "Stock moved and movement history recorded."
    );
  };

  return (
      <PageLayout
          description="The selected source is reduced and the destination is increased in one operation."
          form={
            <form
                onSubmit={submit}
                className="form-grid movement-form"
            >
              <StyleSelect
                  styles={styles}
                  value={form.styleId}
                  onChange={(value) =>
                      setForm({
                        ...form,
                        styleId: value,
                      })
                  }
              />

              <HolderFields
                  label="From"
                  value={form.from}
                  partners={partners}
                  onChange={(from) =>
                      setForm({
                        ...form,
                        from,
                      })
                  }
              />

              <HolderFields
                  label="To"
                  value={form.to}
                  partners={partners}
                  onChange={(to) =>
                      setForm({
                        ...form,
                        to,
                      })
                  }
              />

              <NumberInput
                  label="Quantity"
                  value={form.quantity}
                  onChange={(value) =>
                      setForm({
                        ...form,
                        quantity: value,
                      })
                  }
              />

              <TextInput
                  label="Remarks"
                  value={form.remarks}
                  onChange={(value) =>
                      setForm({
                        ...form,
                        remarks: value,
                      })
                  }
              />

              <SubmitButton
                  label="Move stock"
              />
            </form>
          }
      />
  );
}


/* =========================================================
   WASTAGE
   ========================================================= */

function WastagePage({
                       credentials,
                       styles,
                       partners,
                       runAction,
                     }) {
  const [form, setForm] = useState({
    styleId: "",
    stage: "WEAVING",
    source: emptyHolder(),
    responsiblePartnerId: "",
    quantity: "",
    reason: "",
  });

  const submit = (event) => {
    event.preventDefault();

    const body = {
      styleId: Number(form.styleId),

      stage: form.stage,

      ...holderPayload(
          "source",
          form.source
      ),

      responsiblePartnerId:
          form.responsiblePartnerId
              ? Number(
                  form.responsiblePartnerId
              )
              : null,

      quantity: Number(form.quantity),

      reason: form.reason,
    };

    runAction(
        async () => {
          await postJson(
              "/wastage",
              credentials,
              body
          );

          setForm({
            styleId: "",
            stage: "WEAVING",
            source: emptyHolder(),
            responsiblePartnerId: "",
            quantity: "",
            reason: "",
          });
        },
        "Wastage recorded and source balance reduced."
    );
  };

  return (
      <PageLayout
          description="Every wastage entry reduces the selected source balance."
          form={
            <form
                onSubmit={submit}
                className="form-grid movement-form"
            >
              <StyleSelect
                  styles={styles}
                  value={form.styleId}
                  onChange={(value) =>
                      setForm({
                        ...form,
                        styleId: value,
                      })
                  }
              />

              <SelectInput
                  label="Wastage stage"
                  value={form.stage}
                  onChange={(value) =>
                      setForm({
                        ...form,
                        stage: value,
                      })
                  }
                  options={WASTAGE_STAGES}
              />

              <HolderFields
                  label="Source"
                  value={form.source}
                  partners={partners}
                  onChange={(source) =>
                      setForm({
                        ...form,
                        source,
                      })
                  }
              />

              <OptionalPartnerSelect
                  label="Responsible partner (optional)"
                  partners={partners}
                  value={
                    form.responsiblePartnerId
                  }
                  onChange={(value) =>
                      setForm({
                        ...form,
                        responsiblePartnerId:
                        value,
                      })
                  }
              />

              <NumberInput
                  label="Quantity"
                  value={form.quantity}
                  onChange={(value) =>
                      setForm({
                        ...form,
                        quantity: value,
                      })
                  }
              />

              <TextInput
                  label="Reason"
                  value={form.reason}
                  onChange={(value) =>
                      setForm({
                        ...form,
                        reason: value,
                      })
                  }
                  required
              />

              <SubmitButton
                  label="Record wastage"
              />
            </form>
          }
      />
  );
}


/* =========================================================
   ALTERS
   ========================================================= */

function AltersPage({
                      credentials,
                      styles,
                      partners,
                      runAction,
                    }) {
  const [form, setForm] = useState({
    styleId: "",
    quantity: "",
    faultType: "WASHER",
    responsiblePartnerId: "",
    remarks: "",
  });

  const matchingPartners = useMemo(
      () =>
          partners.filter(
              (partner) =>
                  partner.partnerType ===
                  form.faultType
          ),
      [
        partners,
        form.faultType,
      ]
  );

  const submit = (event) => {
    event.preventDefault();

    runAction(
        async () => {
          await postJson(
              "/alters",
              credentials,
              {
                ...form,
                styleId: Number(
                    form.styleId
                ),
                quantity: Number(
                    form.quantity
                ),
                responsiblePartnerId:
                    Number(
                        form.responsiblePartnerId
                    ),
              }
          );

          setForm({
            styleId: "",
            quantity: "",
            faultType: "WASHER",
            responsiblePartnerId: "",
            remarks: "",
          });
        },
        "Alter record added."
    );
  };

  return (
      <PageLayout
          description="Record affected pieces and identify whether the washer or fabricator is responsible."
          form={
            <form
                onSubmit={submit}
                className="form-grid"
            >
              <StyleSelect
                  styles={styles}
                  value={form.styleId}
                  onChange={(value) =>
                      setForm({
                        ...form,
                        styleId: value,
                      })
                  }
              />

              <NumberInput
                  label="Quantity"
                  value={form.quantity}
                  onChange={(value) =>
                      setForm({
                        ...form,
                        quantity: value,
                      })
                  }
              />

              <SelectInput
                  label="Fault type"
                  value={form.faultType}
                  onChange={(value) =>
                      setForm({
                        ...form,
                        faultType: value,
                        responsiblePartnerId: "",
                      })
                  }
                  options={[
                    "WASHER",
                    "FABRICATOR",
                  ]}
              />

              <SelectInput
                  label="Responsible partner"
                  value={
                    form.responsiblePartnerId
                  }
                  onChange={(value) =>
                      setForm({
                        ...form,
                        responsiblePartnerId:
                        value,
                      })
                  }
                  options={matchingPartners.map(
                      (partner) => ({
                        value: partner.id,
                        label: partner.name,
                      })
                  )}
              />

              <TextInput
                  label="Remarks"
                  value={form.remarks}
                  onChange={(value) =>
                      setForm({
                        ...form,
                        remarks: value,
                      })
                  }
              />

              <SubmitButton
                  label="Record alter"
              />
            </form>
          }
      />
  );
}


/* =========================================================
   PRICING
   ========================================================= */

function PricingPage({
                       credentials,
                       styles,
                       runAction,
                     }) {
  const [form, setForm] = useState({
    styleId: "",
    costPerPiece: "",
    sellingPricePerPiece: "",
  });

  const submit = (event) => {
    event.preventDefault();

    runAction(
        async () => {
          await postJson(
              "/pricing",
              credentials,
              {
                style: {
                  id: Number(
                      form.styleId
                  ),
                },

                costPerPiece: Number(
                    form.costPerPiece
                ),

                sellingPricePerPiece:
                    Number(
                        form.sellingPricePerPiece
                    ),
              }
          );

          setForm({
            styleId: "",
            costPerPiece: "",
            sellingPricePerPiece: "",
          });
        },
        "Style pricing saved."
    );
  };

  return (
      <PageLayout
          description="Enter cost and selling price per piece for each style."
          form={
            <form
                onSubmit={submit}
                className="form-grid"
            >
              <StyleSelect
                  styles={styles}
                  value={form.styleId}
                  onChange={(value) =>
                      setForm({
                        ...form,
                        styleId: value,
                      })
                  }
              />

              <NumberInput
                  label="Cost per piece"
                  value={form.costPerPiece}
                  onChange={(value) =>
                      setForm({
                        ...form,
                        costPerPiece: value,
                      })
                  }
                  step="0.01"
              />

              <NumberInput
                  label="Selling price per piece"
                  value={
                    form.sellingPricePerPiece
                  }
                  onChange={(value) =>
                      setForm({
                        ...form,
                        sellingPricePerPiece:
                        value,
                      })
                  }
                  step="0.01"
              />

              <SubmitButton
                  label="Save pricing"
              />
            </form>
          }
      />
  );
}


/* =========================================================
   REPORTS
   ========================================================= */

function ReportsPage({
                       credentials,
                     }) {
  const [report, setReport] =
      useState("movements");

  const [rows, setRows] =
      useState([]);

  const [error, setError] =
      useState("");

  const [loading, setLoading] =
      useState(false);

  const reportOptions = [
    "movements",
    "partner-stock",
    "wastage",
    "alters",
    { value: "samples", label: "Main Stock" },
    "normal-stock",
    "inventory-valuation",
  ];

  const load = async () => {
    setError("");
    setLoading(true);

    try {
      const data = await apiRequest(
          `/reports/${report}`,
          credentials
      );

      setRows(
          Array.isArray(data)
              ? data
              : []
      );
    } catch (requestError) {
      setError(
          requestError.message
      );
      setRows([]);
    } finally {
      setLoading(false);
    }
  };

  const displayRows = rows.map(
      (row) =>
          Object.entries(row)
              .filter(
                  ([key]) =>
                      key !== "id"
              )
              .map(
                  ([key, value]) =>
                      `${formatLabel(
                          key
                      )}: ${formatValue(
                          value
                      )}`
              )
  );

  return (
      <div>
        <div className="report-controls">
          <SelectInput
              label="Report"
              value={report}
              onChange={(value) => {
                setReport(value);
                setRows([]);
                setError("");
              }}
              options={reportOptions}
          />

          <button
              className="primary-button compact"
              onClick={load}
              disabled={loading}
          >
            {loading
                ? "Loading…"
                : "Load report"}
          </button>
        </div>

        {error && (
            <div className="notice error">
              {error}
            </div>
        )}

        {report ===
            "inventory-valuation" && (
                <div className="info-card">
                  <h2>
                    Inventory valuation
                  </h2>

                  <p>
                    This report shows the current
                    inventory value calculated from
                    your style pricing and stock
                    balances.
                  </p>
                </div>
            )}

        <SimpleTable
            columns={[
              "Report data",
            ]}
            rows={displayRows.map(
                (row) => [
                  row.join(" · "),
                ]
            )}
        />
      </div>
  );
}


/* =========================================================
   SHARED LAYOUT
   ========================================================= */

function PageLayout({
                      description,
                      form,
                      table,
                    }) {
  return (
      <div className="page-layout">
        {description && (
            <p className="page-intro">
              {description}
            </p>
        )}

        <section className="panel">
          <h2>
            Add or record
          </h2>

          {form}
        </section>

        {table && (
            <section className="panel">
              <h2>
                Current records
              </h2>

              {table}
            </section>
        )}
      </div>
  );
}


/* =========================================================
   HOLDER FIELDS
   ========================================================= */

function HolderFields({
                        label,
                        value,
                        partners,
                        onChange,
                      }) {
  const matchingPartners =
      partners.filter(
          (partner) =>
              partner.partnerType ===
              value.holderType &&
              partner.active
      );

  return (
      <fieldset className="holder-fields">
        <legend>
          {label}
        </legend>

        <SelectInput
            label="Holder"
            value={value.holderType}
            onChange={(holderType) =>
                onChange({
                  holderType,
                  location: "SHOP",
                  partnerId: "",
                })
            }
            options={HOLDER_TYPES}
        />

        {value.holderType ===
        "LOCATION" ? (
            <SelectInput
                label="Location"
                value={value.location}
                onChange={(location) =>
                    onChange({
                      ...value,
                      location,
                    })
                }
                options={LOCATIONS}
            />
        ) : (
            <SelectInput
                label={`${value.holderType.toLowerCase()} name`}
                value={value.partnerId}
                onChange={(partnerId) =>
                    onChange({
                      ...value,
                      partnerId,
                    })
                }
                options={matchingPartners.map(
                    (partner) => ({
                      value: partner.id,
                      label: partner.name,
                    })
                )}
            />
        )}
      </fieldset>
  );
}


/* =========================================================
   HOLDER PAYLOAD
   ========================================================= */

function holderPayload(
    prefix,
    holder
) {
  return {
    [`${prefix}HolderType`]:
    holder.holderType,

    [`${prefix}Location`]:
        holder.holderType ===
        "LOCATION"
            ? holder.location
            : null,

    [`${prefix}PartnerId`]:
        holder.holderType ===
        "LOCATION"
            ? null
            : Number(
                holder.partnerId
            ),
  };
}


/* =========================================================
   FORM COMPONENTS
   ========================================================= */

function TextInput({
                     label,
                     value,
                     onChange,
                     required = false,
                   }) {
  return (
      <label>
        {label}

        <input
            value={value}
            onChange={(event) =>
                onChange(
                    event.target.value
                )
            }
            required={required}
        />
      </label>
  );
}


function NumberInput({
                       label,
                       value,
                       onChange,
                       step = "1",
                     }) {
  return (
      <label>
        {label}

        <input
            type="number"
            min="0"
            step={step}
            value={value}
            onChange={(event) =>
                onChange(
                    event.target.value
                )
            }
            required
        />
      </label>
  );
}


function SelectInput({
                       label,
                       value,
                       onChange,
                       options,
                     }) {
  return (
      <label>
        {label}

        <select
            value={value}
            onChange={(event) =>
                onChange(
                    event.target.value
                )
            }
            required
        >
          <option value="">
            Select…
          </option>

          {options.map(
              (option) => {
                const normalized =
                    typeof option ===
                    "object"
                        ? option
                        : {
                          value:
                          option,
                          label:
                              String(
                                  option
                              ).replaceAll(
                                  "_",
                                  " "
                              ),
                        };

                return (
                    <option
                        key={
                          normalized.value
                        }
                        value={
                          normalized.value
                        }
                    >
                      {
                        normalized.label
                      }
                    </option>
                );
              }
          )}
        </select>
      </label>
  );
}


function StyleSelect({
                       styles,
                       value,
                       onChange,
                     }) {
  return (
      <SelectInput
          label="Style"
          value={value}
          onChange={onChange}
          options={styles.map(
              (style) => ({
                value: style.id,
                label: `${style.styleCode} — ${style.colour}`,
              })
          )}
      />
  );
}


function OptionalPartnerSelect({
                                 label,
                                 partners,
                                 value,
                                 onChange,
                               }) {
  return (
      <label>
        {label}

        <select
            value={value}
            onChange={(event) =>
                onChange(
                    event.target.value
                )
            }
        >
          <option value="">
            None
          </option>

          {partners.map(
              (partner) => (
                  <option
                      key={partner.id}
                      value={partner.id}
                  >
                    {partner.partnerType} —{" "}
                    {partner.name}
                  </option>
              )
          )}
        </select>
      </label>
  );
}


function SubmitButton({
                        label,
                      }) {
  return (
      <button
          className="primary-button"
          type="submit"
      >
        {label}
      </button>
  );
}


/* =========================================================
   TABLE
   ========================================================= */

function SimpleTable({
                       columns,
                       rows,
                     }) {
  return (
      <div className="table-wrap">
        <table>
          <thead>
          <tr>
            {columns.map(
                (column) => (
                    <th key={column}>
                      {column}
                    </th>
                )
            )}
          </tr>
          </thead>

          <tbody>
          {rows.length ? (
              rows.map(
                  (
                      row,
                      index
                  ) => (
                      <tr key={index}>
                        {row.map(
                            (
                                cell,
                                cellIndex
                            ) => (
                                <td
                                    key={
                                      cellIndex
                                    }
                                >
                                  {cell}
                                </td>
                            )
                        )}
                      </tr>
                  )
              )
          ) : (
              <tr>
                <td
                    colSpan={
                      columns.length
                    }
                    className="empty-cell"
                >
                  No records yet.
                </td>
              </tr>
          )}
          </tbody>
        </table>
      </div>
  );
}


/* =========================================================
   FORMATTERS
   ========================================================= */

function formatValue(value) {
  if (
      value === null ||
      value === undefined ||
      value === ""
  ) {
    return "—";
  }

  if (
      typeof value ===
      "object"
  ) {
    return (
        value.name ||
        value.styleCode ||
        JSON.stringify(value)
    );
  }

  return String(value).replaceAll(
      "_",
      " "
  );
}


function formatLabel(value) {
  return String(value)
      .replace(/([A-Z])/g, " $1")
      .replaceAll("_", " ")
      .replace(/^./, (char) =>
          char.toUpperCase()
      );
}


function formatCurrency(value) {
  const number =
      Number(value || 0);

  return new Intl.NumberFormat(
      "en-IN",
      {
        style: "currency",
        currency: "INR",
        maximumFractionDigits: 2,
      }
  ).format(number);
}


export default App;