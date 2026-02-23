from app.pipeline.ingest import DocumentIngestor
from app.pipeline.chunker import DocumentChunker
from app.pipeline.embedder import DocumentEmbedder
from app.pipeline.retriever import DocumentRetriever
from app.pipeline.extractor import TokenomicsExtractor
from app.schemas.tokenomics import TokenomicsOutput
import os
import shutil

class RagOrchestrator:
    def __init__(self):
        self.ingestor = DocumentIngestor()
        self.chunker = DocumentChunker()
        self.embedder = DocumentEmbedder()
        self.extractor = TokenomicsExtractor()

    async def process_document(self, file_path: str) -> TokenomicsOutput:
        """
        Runs the full RAG pipeline on a document.
        1. Ingest
        2. Chunk
        3. Embed & Vector Store
        4. Retrieve relevant context
        5. Extract structured data
        """
        # 1. Ingest
        raw_text = self.ingestor.extract_text(file_path)
        if not raw_text.strip():
            raise ValueError("Document is empty or text could not be extracted")

        # 2. Chunk
        chunks = self.chunker.chunk_text(raw_text)

        # 3. Embed & Store (Transient store for this document)
        vector_store = self.embedder.create_vector_store(chunks)

        # 4. Retrieve (Searching for tokenomics key terms)
        retriever = DocumentRetriever(vector_store)
        relevant_chunks = retriever.retrieve_relevant_chunks(
            "Extract token name, total supply, inflation, vesting, staking, utility, governance, and risks.",
            k=8
        )

        # 5. Extract
        result = self.extractor.extract(relevant_chunks)
        
        return result
