INSERT INTO medication (id, name, description, category) VALUES 
('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'Ibuprofeno', '600mg', 'ANTIINFLAMMATORY')
ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name, description = EXCLUDED.description, category = EXCLUDED.category;

INSERT INTO medication (id, name, description, category) VALUES 
('c56a4180-65aa-42ec-a945-5fd21dec0538', 'Paracetamol', '1g', 'ANALGESIC')
ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name, description = EXCLUDED.description, category = EXCLUDED.category;

INSERT INTO medication (id, name, description, category) VALUES 
('a832924c-2821-4d33-911e-a4f23b2c3d47', 'Nolotil', 'Metamizol', 'ANALGESIC')
ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name, description = EXCLUDED.description, category = EXCLUDED.category;
